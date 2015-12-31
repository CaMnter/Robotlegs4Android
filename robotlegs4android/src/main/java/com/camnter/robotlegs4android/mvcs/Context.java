/*
 * Copyright (C) 2015 CaMnter 421482590@qq.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camnter.robotlegs4android.mvcs;

import android.view.View;

import com.camnter.robotlegs4android.adapter.SwiftSuspendersInjector;
import com.camnter.robotlegs4android.adapter.SwiftSuspendersReflector;
import com.camnter.robotlegs4android.base.CommandMap;
import com.camnter.robotlegs4android.base.ContextBase;
import com.camnter.robotlegs4android.base.ContextError;
import com.camnter.robotlegs4android.base.ContextEvent;
import com.camnter.robotlegs4android.base.EventMap;
import com.camnter.robotlegs4android.base.MediatorMap;
import com.camnter.robotlegs4android.base.ViewMap;
import com.camnter.robotlegs4android.core.ICommandMap;
import com.camnter.robotlegs4android.core.IContext;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IEventMap;
import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.core.IMediatorMap;
import com.camnter.robotlegs4android.core.IReflector;
import com.camnter.robotlegs4android.core.IViewMap;

/**
 * Description：Context
 * Created by：CaMnter
 */
public abstract class Context extends ContextBase implements IContext {

    protected IInjector _injector;
    protected IReflector _reflector;
    protected Boolean _autoStartup;
    protected Object _contextView;
    protected ICommandMap _commandMap;
    protected IMediatorMap _mediatorMap;

    /**
     * private
     */
    protected IViewMap _viewMap;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Abstract Context Implementation
     * 抽象的背景下实现
     * Extend this class to create a Framework or Application context
     * 扩展这个类来创建一个框架或应用程序上下文
     *
     * @param contextView The root view node of the context. The context will listen for
     *                    ADDED_TO_STAGE events on this node
     *                    视图上下文的根节点。上下文将监听ADDED_TO_STAGE事件在这个节点
     * @param autoStartup Should this context automatically invoke it's
     *                    <code>startup</code> method when it's <code>contextView</code>
     *                    arrives on Stage? 应该contextView到达阶段这种情况下,自动调用它的startup方法吗?
     */
    public Context(Object contextView, Boolean autoStartup) {
        super();
        this._contextView = contextView;
        this._autoStartup = autoStartup;
        if (this._contextView != null) {
            this.mapInjections();
            this.checkAutoStartup();
        }
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * The Startup Hook
     * 启动
     * Override this in your Application context
     * 重写在你的应用程序上下文
     * event Startup complete ContextEvent.STARTUP_COMPLETE Dispatched at the
     * end of the <code>startup()</code> method's execution. This is
     * often used to trigger startup/bootstrapping commands by wiring
     * them to this event and calling <code>super.startup()</code> in the
     * last line of your <code>startup()</code> override.
     * 在startup方法的执行结束后开始完成ContextEvent
     * .STARTUP_COMPLETE的分发.这通常是用来触发启动/引导命令通过布线这一事件和调用super
     * .startup(),在startup总结覆盖的最后一行.
     */
    public void startup() {
        this.dispatchEvent(new ContextEvent(ContextEvent.STARTUP_COMPLETE));
        this.setMvcRelation();
    }

    /**
     * set your mvc relation
     * 设置你的mvc关系
     * Add the view map
     * Link the View and View the corresponding Mediator
     * 添加view映射
     * 将View 和 View 对应的 Mediator 联系起来
     * Injection as an singleton, instantiate the singleton
     * 注入实例，实例化单例
     * Add Event (Event) with the connection of the Command
     * 添加事件（Event）与Command的联系
     */
    public abstract void setMvcRelation();

    /**
     * The Shutdown Hook
     * 关闭
     * Override this in your Application context
     * 重写在你的应用程序上下文
     */
    public void shutdown() {
        this.dispatchEvent(new ContextEvent(ContextEvent.SHUTDOWN_COMPLETE));
    }

    /**
     * The <code>DisplayObjectContainer</code> that scopes this
     * <code>IContext</code>
     * IContext范围的DisplayObjectContainer
     *
     * @return The Context View
     */
    public Object getContextView() {
        return this._contextView;
    }

    /**
     * Set The ContextView
     * 设置ContextView
     *
     * @param value value
     */
    public void setContextView(View value) {
        if (value == this._contextView)
            return;

        if (this._contextView != null) {
            throw new ContextError(ContextError.E_CONTEXT_VIEW_OVR);
        }

        this._contextView = value;
        this.mapInjections();
        this.checkAutoStartup();
    }

    // ---------------------------------------------------------------------
    // Protected, Lazy Getters and Setters
    // ---------------------------------------------------------------------

    /**
     * @return The <code>IInjector</code> for this <code>IContext</code>
     */
    protected IInjector getInjector() {
        if (this._injector != null) {
            return this._injector;
        } else {
            this._injector = this.createInjector();
            return this._injector;
        }
    }

    /**
     * @param value value
     */
    protected void setInjector(IInjector value) {
        this._injector = value;
    }

    /**
     * @return The <code>IReflector</code> for this <code>IContext</code>
     */
    protected IReflector getReflector() {
        if (this._reflector != null) {
            return this._reflector;
        } else {
            this._reflector = new SwiftSuspendersReflector();
            return this._reflector;
        }
    }

    /**
     * @param value value
     *              private
     */
    protected void setReflector(IReflector value) {
        this._reflector = value;
    }

    /**
     * The <code>ICommandMap</code> for this <code>IContext</code>
     *
     * @return ICommandMap
     */
    protected ICommandMap getCommandMap() {
        if (this._commandMap != null) {
            return this._commandMap;
        } else {
            this._commandMap = new CommandMap(this.getEventDispatcher(),
                    this.createChildInjector(), this.getReflector());
            return this._commandMap;
        }
    }

    /**
     * private
     * @param value value
     */
    protected void setCommandMap(ICommandMap value) {
        this._commandMap = value;
    }

    /**
     * The <code>IMediatorMap</code> for this <code>IContext</code>
     * @return IMediatorMap
     */
    protected IMediatorMap getMediatorMap() {
        if (this._mediatorMap != null) {
            return this._mediatorMap;
        } else {
            this._mediatorMap = new MediatorMap(this.getContextView(),
                    this.createChildInjector(), this.getReflector());
            return this._mediatorMap;
        }
    }

    /**
     * @param value value
     *              private
     */
    protected void setMediatorMap(IMediatorMap value) {
        this._mediatorMap = value;
    }

    /**
     * The <code>IViewMap</code> for this <code>IContext</code>
     *
     * @return IViewMap
     */
    protected IViewMap getViewMap() {
        if (this._viewMap != null) {
            return this._viewMap;
        } else {
            this._viewMap = new ViewMap(this.getContextView(),
                    this.getInjector());
            return this._viewMap;
        }
    }

    /**
     * @param value value
     */
    protected void setViewMap(IViewMap value) {
        this._viewMap = value;
    }

    // ---------------------------------------------------------------------
    // Framework Hooks
    // ---------------------------------------------------------------------

    /**
     * Injection Mapping Hook
     * Override this in your Framework context to change the default
     * configuration
     * 覆写这个在你的框架Context去改变默认配置
     * Beware of collisions in your container
     * 谨防碰撞在你的容器
     */
    protected void mapInjections() {
        this.getInjector().mapValue(IReflector.class, this.getReflector(), "");
        this.getInjector().mapValue(IInjector.class, this.getInjector(), "");
        this.getInjector().mapValue(IEventDispatcher.class,
                this.getEventDispatcher(), "");
        this.getInjector().mapValue(Object.class, this.getContextView(), "");
        this.getInjector()
                .mapValue(ICommandMap.class, this.getCommandMap(), "");
        this.getInjector().mapValue(IMediatorMap.class, this.getMediatorMap(),
                "");
        this.getInjector().mapValue(IViewMap.class, this.getViewMap(), "");
        this.getInjector().mapClass(IEventMap.class, EventMap.class, "");
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * private
     */
    protected void checkAutoStartup() {
        if (this._autoStartup && (this.getContextView() != null)) {
            this.startup();
        }
    }

    /**
     * private
     * @return IInjector
     */
    protected IInjector createInjector() {
        IInjector injector = new SwiftSuspendersInjector(null);
        return injector;
    }

    /**
     * private
     * @return IInjector
     */
    protected IInjector createChildInjector() {
        return this.getInjector().createChild();
    }

    /**
     * @param view view
     */
    public void injectMediator(Object view) {
        this.getMediatorMap().addMediator(view);
    }

    /**
     * @param view view
     */
    public void removeMediator(Object view) {
        this.getMediatorMap().unInjectMediator(view);
    }

}
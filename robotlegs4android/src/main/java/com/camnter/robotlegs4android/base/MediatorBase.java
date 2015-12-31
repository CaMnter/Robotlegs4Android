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

package com.camnter.robotlegs4android.base;

import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IMediator;

/**
 * Description：MediatorBase
 * Created by：CaMnter
 */
public class MediatorBase implements IMediator {

    /**
     * Flex framework work-around part #1
     * Flex框架的变通办法部分 #1
     */
    protected static Class<?> uiComponentClass;

    /**
     * Flex framework work-around part #2
     * Flex框架的变通办法部分 #2
     */
    protected static final Boolean flexAvailable = MediatorBase.checkFlex();

    /**
     * Internal
     * This Mediator's View Component - used by the RobotLegs MVCS framework
     * internally. You should declare a dependency on a concrete view component
     * in your implementation instead of working with this property
     * 这个中介的视图组件——Robotlegs4Android(RobotLegs)使用MVC框架内部。你应该声明依赖于一个具体的视图 组件的实现,而不是使用这个属性
     */
    protected Object viewComponent;

    /**
     * Internal
     * In the case of differed instantiation, onRemove might get called before
     * onCreationComplete has fired. This here Boolean helps us track that
     * scenario.
     * 不同的实例化,onRemove之前会通知onCreationComplete已经解除了。这里Boolean帮助我们跟踪这种情况。
     */
    protected Boolean removed;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Creates a new <code>IMediator</code> object
     * 创建一个新的IMediator实例对象
     */
    public MediatorBase() {

    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #preRegister}
     */
    @Override
    public void preRegister() {
        this.removed = false;
        this.onRegister();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #onRegister}
     */
    @Override
    public void onRegister() {

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #preRemove}
     */
    @Override
    public void preRemove() {
        this.removed = true;
        this.onRemove();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #onRemove}
     */
    @Override
    public void onRemove() {

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #getViewComponent}
     */
    @Override
    public Object getViewComponent() {
        return this.viewComponent;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediator #setViewComponent}
     */
    @Override
    public void setViewComponent(Object viewComponent) {
        this.viewComponent = viewComponent;
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * When MediatorBase type has been created
     * MediatorBase类型创建完成时
     *
     * @author CaMnter
     */
    private final class CreationCompleteListener extends Listener {

        public CreationCompleteListener(String type, String name) {
            super(type, name);
        }

        @Override
        public void onHandle(Event event) {
            MediatorBase.this.onCreationComplete(event);
        }

    }

    /**
     * Flex framework work-around part #3
     * Flex框架的变通办法部分 #3
     * Checks for availability of the Flex framework by trying to get the class
     * for UIComponent.
     * 检查可用性的Flex框架试图获得UIComponent类。
     *
     * @return Whether can Flex UIComponent class.
     */
    protected static Boolean checkFlex() {

        return true;

        // try
        // {
        // UIComponentClass =
        // (Class)getDefinitionByName("mx.core::UIComponent");
        // }
        // catch (Error error)
        // {
        // // do nothing
        // }
        // return UIComponentClass != null;

    }

    /**
     * Flex framework work-around part #4
     * Flex框架的变通办法部分 #4
     * <code>FlexEvent.CREATION_COMPLETE</code> handler for this Mediator's View
     * Component
     *
     * @param event The Flex <code>FlexEvent</code> event Flex的FlexEvent事件类型
     */
    protected void onCreationComplete(Event event) {
        ((IEventDispatcher) event.getTarget()).removeEventListener(
                "creationComplete", new CreationCompleteListener(
                        "creationComplete", "onCreationComplete"), false);
        if (!this.removed) {
            this.onRegister();
        }
    }

}
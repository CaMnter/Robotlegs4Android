/*
 * Copyright (C) 2015 CaMnter yuanyu.camnter@gmail.com
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

import android.view.View;

import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.core.IViewMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Description：ViewMap
 * Created by：CaMnter
 */
public class ViewMap extends ViewMapBase implements IViewMap {

    /**
     * private
     */
    protected ArrayList<String> mappedPackages;

    /**
     * private
     */
    protected Map<String, Object> mappedTypes;

    /**
     * private
     */
    protected Map<String, Object> injectedViews;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Creates a new <code>ViewMap</code> object
     * 创建一个新的ViewMap对象
     *
     * @param contextView The root view node of the context. The map will listen for
     *                    ADDED_TO_STAGE events on this node
     *                    上下文的根节点视图。Map将在这个节点监听ADDED_TO_STAGE事件。
     * @param injector    An <code>IInjector</code> to use for this context
     *                    这种情况下的IInjector使用
     */
    public ViewMap(Object contextView, IInjector injector) {
        super(contextView, injector);
        // mappings - if you can do it with fewer dictionaries you get a prize
        this.mappedPackages = new ArrayList<>();
        this.mappedTypes = new HashMap<>();
        this.injectedViews = new WeakHashMap<>();
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #mapPackage}
     */
    @Override
    public void mapPackage(String packageName) {
        if (this.mappedPackages.indexOf(packageName) == -1) {
            this.mappedPackages.add(packageName);
            this.viewListenerCount++;
            if (this.viewListenerCount == 1) {
                this.addListeners();
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #unmapPackage}
     */
    @Override
    public void unmapPackage(String packageName) {
        int index = this.mappedPackages.indexOf(packageName);
        if (index > -1) {
            this.mappedPackages.remove(index);
            this.viewListenerCount--;
            if (this.viewListenerCount == 0) {
                this.removeListeners();
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #mapType}
     */
    @Override
    public void mapType(Class<?> type) {
        if (this.mappedTypes.get(type.hashCode() + "") != null)
            return;

        this.mappedTypes.put(type.hashCode() + "", type);
        this.viewListenerCount++;
        if (this.viewListenerCount == 1) {
            this.addListeners();
        }

        /*
         * This was a bad idea - causes unexpected eager instantiation of object
         * graph
         * 这是一个坏主意-使意想不到的急切的实例化对象图
         */
        if ((this.getContextView() != null)
                && (type.isInstance(this.getContextView()))) {
            this.injectInto(this.getContextView());
        }

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #unmapType}
     */
    @Override
    public void unmapType(Class<?> type) {
        Class<?> mapping = (Class<?>) this.mappedTypes
                .get(type.hashCode() + "");
        this.mappedTypes.remove(this.hashCode() + "");
        if (mapping != null) {
            this.viewListenerCount--;
            if (this.viewListenerCount == 0) {
                this.removeListeners();
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #hasType}
     */
    @Override
    public Boolean hasType(Class<?> type) {
        return (this.mappedTypes.get(type.hashCode() + "") != null);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #hasPackage}
     */
    @Override
    public Boolean hasPackage(String packageName) {
        return this.mappedPackages.indexOf(packageName) > -1;
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #addListeners}
     *
     * private
     */
    @Override
    protected void addListeners() {
        if ((this.getContextView() != null) && this.getEnabled()) {
            EventDispatcher
                    .setDispatcher(this.getContextView().hashCode() + "");
            EventDispatcher dispatcher = EventDispatcher.getDispatcher(this
                    .getContextView().hashCode() + "");
            if (dispatcher != null) {
                dispatcher.addEventListener(Event.ADDED_TO_STAGE,
                        new ViewAddedListener(Event.ADDED_TO_STAGE,
                                "onViewAdded"), this.useCapture, 0, true);
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #removeListeners}
     *
     * private
     */
    @Override
    protected void removeListeners() {
        if (this.getContextView() != null) {
            EventDispatcher dispatcher = EventDispatcher.getDispatcher(this
                    .getContextView().hashCode() + "");
            if (dispatcher != null) {
                dispatcher.removeEventListener(Event.ADDED_TO_STAGE,
                        new ViewAddedListener(Event.ADDED_TO_STAGE,
                                "onViewAdded"), this.useCapture);
            }
        }
    }

    /**
     * The listener listens for whether the view is added
     * 用于监听view是否被添加的listener
     *
     * @author CaMnter
     */
    private final class ViewAddedListener extends Listener {
        public ViewAddedListener(String type, String name) {
            super(type, name);
        }

        @Override
        public void onHandle(Event event) {
            ViewMap.this.onViewAdded(event);
        }

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IViewMap #onViewAdded}
     *
     * private
     */
    @Override
    protected void onViewAdded(Event e) {
        View target = (View) e.getTarget();
        if (this.injectedViews.get(target.hashCode() + "") != null)
            return;

        for (Object type : this.mappedTypes.values()) {
            if (((Class<?>) type).isInstance(target)) {
                this.injectInto(target);
                return;
            }
        }

        int len = this.mappedPackages.size();
        if (len > 0) {
            String className = target.getClass().getName();
            for (int i = 0; i < len; i++) {
                String packageName = this.mappedPackages.get(i);
                if (className.indexOf(packageName) == 0) {
                    this.injectInto(target);
                    return;
                }
            }
        }
    }

    /**
     * inject the contextView
     * Then save the hashCode joining injectedViews
     * 注入ContextView
     * 然后将其hashCode加入到injectedViews里保存
     *
     * @param target The ContextView
     */
    protected void injectInto(Object target) {
        this.injector.injectInto(target);
        this.injectedViews.put(target.hashCode() + "", true);
    }

}

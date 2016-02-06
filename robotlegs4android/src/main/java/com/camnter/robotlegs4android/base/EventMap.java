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

import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IEventMap;
import com.camnter.robotlegs4android.core.IListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：EventMap
 * Created by：CaMnter
 */
public class EventMap implements IEventMap {

    /**
     * The <code>IEventDispatcher</code>
     */
    protected IEventDispatcher eventDispatcher;

    /**
     * private
     */
    protected Boolean _dispatcherListeningEnabled = true;

    /**
     * private
     */
    protected List<Object> listeners;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Creates a new <code>EventMap</code> object
     * 创建一个新的EventMap对象
     *
     * @param eventDispatcher The <code>IEventDispatcher</code> type. IEventDispatcher类型
     */
    public EventMap(IEventDispatcher eventDispatcher) {
        this.listeners = new ArrayList<>();
        this.eventDispatcher = eventDispatcher;
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * Get the dispenser is listening state
     * 获取分发器正在监听的状态
     *
     * @return Is shared dispatcher listening allowed? 共享允许分发器监听?
     */
    public Boolean getDispatcherListeningEnabled() {
        return this._dispatcherListeningEnabled;
    }

    /**
     * Set the dispenser is listening state
     * 设置分发器正在监听的状态
     *
     * @param value The status value 状态值
     */
    public void setDispatcherListeningEnabled(Boolean value) {
        this._dispatcherListeningEnabled = value;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventMap #mapListener}
     * The same as calling <code>addEventListener</code> directly on the
     * <code>IEventDispatcher</code>, but keeps a list of listeners for easy
     * (usually automatic) removal.
     * 和直接在IEventDispatcher上调用addEventListener一样,但保持简单的侦听器列表(通常是自动)。
     *
     * @param dispatcher       The <code>IEventDispatcher</code> to listen to
     * @param type             The <code>Event</code> type to listen for
     * @param listener         The <code>Event</code> handler
     * @param eventClass       Optional Event class for a stronger mapping. Defaults to
     *                         <code>Event</code>. 可选事件类更强的映射。默认值为Event
     * @param useCapture       |false
     * @param priority         |0
     * @param useWeakReference |true
     */
    @Override
    public void mapListener(IEventDispatcher dispatcher, String type,
                            IListener listener, Class<?> eventClass, Boolean useCapture,
                            int priority, Boolean useWeakReference) {
        if ((!this.getDispatcherListeningEnabled()) && (dispatcher == this.eventDispatcher)) {
            throw new ContextError(ContextError.E_EVENTMAP_NOSNOOPING);
        }
        if (eventClass == null) {
            eventClass = Event.class;
        }
        Params params;
        int i = this.listeners.size();

        while (i-- > 0) {
            params = (Params) this.listeners.get(i);
            if ((params.dispatcher == dispatcher) && params.type.equals(type)
                    && (params.listener == listener)
                    && (params.useCapture == useCapture)
                    && (params.eventClass == eventClass))
                return;
        }
        IListener callback = new Callback(listener, eventClass);
        params = new Params();
        params.dispatcher = dispatcher;
        params.type = type;
        params.listener = listener;
        params.useCapture = useCapture;
        params.eventClass = eventClass;
        params.callback = callback;
        this.listeners.add(params);
        dispatcher.addEventListener(type, callback, useCapture, priority,
                useWeakReference);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventMap #unmapListener}
     * The same as calling <code>removeEventListener</code> directly on the
     * <code>IEventDispatcher</code>, but updates our local list of listeners.
     * 和调用IEventDispatcher里的removeEventListener一样,但是更新我们当地的侦听器列表。
     *
     * @param dispatcher The <code>IEventDispatcher</code>
     * @param type       The <code>Event</code> type
     * @param listener   The <code>Event</code> handler
     * @param eventClass Optional Event class for a stronger mapping. Defaults to
     *                   <code>Event</code>. 可选事件类更强的映射。默认值为Event
     * @param useCapture useCapture
     */
    @Override
    public void unmapListener(IEventDispatcher dispatcher, String type,
                              IListener listener, Class<?> eventClass, Boolean useCapture) {
        if (eventClass == null) {
            eventClass = Event.class;
        }
        Params params;
        int i = this.listeners.size();
        while (i-- != 0) {
            params = (Params) this.listeners.get(i);
            if ((params.dispatcher == dispatcher) && params.type.equals(type)
                    && (params.listener == listener)
                    && (params.useCapture == useCapture)
                    && (params.eventClass == eventClass)) {
                dispatcher.removeEventListener(type, params.callback,
                        useCapture);
                this.listeners.remove(i);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventMap #unmapListeners}
     * Removes all listeners registered through <code>mapListener</code>
     * 通过mapListener删除所有的侦听器注册
     */
    @Override
    public void unmapListeners() {
        Params params;
        IEventDispatcher dispatcher;
        while ((this.listeners.size() > 0)
                && ((params = (Params) this.listeners.remove(this.listeners
                .size() - 1)) != null)) {
            dispatcher = params.dispatcher;
            dispatcher.removeEventListener(params.type, params.callback,
                    params.useCapture);
        }
    }

    /**
     * Used to save the EventMap.this.listener set out in the instance of the
     * object
     * 用于保存EventMap.this.listener集合里取出来的实例对象
     *
     * @author CaMnter
     */
    private final class Params {
        public IEventDispatcher dispatcher;
        public String type;
        public IListener listener;
        public Class<?> eventClass;
        public IListener callback;
        public Boolean useCapture;
    }

    /**
     * Used to instantiate the IListener callback in
     * EventMap.Params.this.listener
     * 用于实例化EventMap.Params.this.listener里的IListener callback
     *
     * @author CaMnter
     */
    private final class Callback extends Listener {

        private final IListener listener;
        private final Class<?> eventClass;

        public Callback(IListener listener, Class<?> eventClass) {
            super(listener.getName());
            this.listener = listener;
            this.eventClass = eventClass;
        }

        @Override
        public void onHandle(Event event) {
            EventMap.this.routeEventToListener(event, this.listener,
                    this.eventClass);
        }

    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * Event Handler
     * 事件处理程序
     *
     * @param event              The <code>Event</code> Event事件类型
     * @param listener           listener
     * @param originalEventClass originalEventClass
     */
    protected void routeEventToListener(Event event, IListener listener, Class<?> originalEventClass) {
        if (originalEventClass.isInstance(event)) {
            listener.onHandle(event);
        }
    }

}

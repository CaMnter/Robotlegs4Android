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

import android.util.Log;

import com.camnter.robotlegs4android.core.ICommandMap;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.core.IReflector;
import com.camnter.robotlegs4android.mvcs.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：CommandMap
 * Created by：CaMnter
 */
public class CommandMap implements ICommandMap {

    /**
     * The <code>IEventDispatcher</code> to listen to
     */
    protected IEventDispatcher eventDispatcher;

    /**
     * The <code>IInjector</code> to inject with
     */
    protected IInjector injector;

    /**
     * The <code>IReflector</code> to reflect with
     */
    protected IReflector reflector;

    /**
     * Internal
     * TODO: This needs to be documented 这需要记录
     */
    protected Map<String, Object> eventTypeMap;

    /**
     * Internal
     * Collection of command classes that have been verified to implement an
     * <code>execute</code> method
     * 命令的集合类,已验证实现一个execute方法
     */
    protected Map<String, Object> verifiedCommandClasses;
    protected Map<String, Object> detainedCommands;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Creates a new <code>CommandMap</code> object
     * 创建一个新的CommandMap对象
     *
     * @param eventDispatcher The <code>IEventDispatcher</code> to listen to
     * @param injector        An <code>IInjector</code> to use for this context
     * @param reflector       An <code>IReflector</code> to use for this context
     */
    public CommandMap(IEventDispatcher eventDispatcher, IInjector injector, IReflector reflector) {
        this.eventDispatcher = eventDispatcher;
        this.injector = injector;
        this.reflector = reflector;
        this.eventTypeMap = new HashMap<>();
        this.verifiedCommandClasses = new HashMap<>();
        this.detainedCommands = new HashMap<>();
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #mapEvent}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void mapEvent(String eventType, Class<?> commandClass, Class<?> eventClass, Boolean oneshot) {
        this.verifyCommandClass(commandClass);
        if (eventClass == null) {
            eventClass = Event.class;
        }

        Map<String, Object> eventClassMap = (Map<String, Object>) (this.eventTypeMap
                .get(eventType));
        if (eventClassMap == null) {
            eventClassMap = new HashMap<>();
        }
        Map<String, Object> callbacksByCommandClass = (HashMap<String, Object>) (eventClassMap
                .get(eventClass.hashCode() + ""));
        if (callbacksByCommandClass == null) {
            callbacksByCommandClass = new HashMap<>();
        }
        if (callbacksByCommandClass.get(commandClass.hashCode() + "") != null)
            throw new ContextError(ContextError.E_COMMANDMAP_OVR
                    + " - eventType (" + eventType + ") and Command ("
                    + commandClass + ")");
        MapEventListener callback = new MapEventListener(eventType, "callback",
                commandClass, eventClass, oneshot);
        this.eventDispatcher.addEventListener(eventType, callback, false, 0,
                true);
        callbacksByCommandClass.put(commandClass.hashCode() + "", callback);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #unmapEvent}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void unmapEvent(String eventType, Class<?> commandClass, Class<?> eventClass) {
        Map<String, Object> eventClassMap = (Map<String, Object>) this.eventTypeMap
                .get(eventType);
        if (eventClassMap == null)
            return;

        Map<String, Object> callbacksByCommandClass = (Map<String, Object>) (eventClassMap
                .get(eventClass == null ? Event.class.hashCode() + ""
                        : eventClass.hashCode() + ""));
        if (callbacksByCommandClass == null)
            return;

        Listener callback = (Listener) callbacksByCommandClass.get(commandClass
                .hashCode() + "");
        if (callback == null)
            return;

        this.eventDispatcher.removeEventListener(eventType, callback, false);
        callbacksByCommandClass.remove(commandClass.hashCode() + "");
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #unmapEvents}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void unmapEvents() {
        for (String eventType : this.eventTypeMap.keySet()) {
            Map<String, Object> eventClassMap = (Map<String, Object>) this.eventTypeMap
                    .get(eventType);
            for (Object oCallbacksByCommandClass : eventClassMap.values()) {
                Map<String, Object> callbacksByCommandClass = (Map<String, Object>) oCallbacksByCommandClass;
                for (Object oCallback : callbacksByCommandClass.values()) {
                    Listener callback = (Listener) oCallback;
                    this.eventDispatcher.removeEventListener(eventType,
                            callback, false);
                }
            }
        }
        this.eventTypeMap = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #hasEventCommand}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Boolean hasEventCommand(String eventType, Class<?> commandClass, Class<?> eventClass) {
        Map<String, Object> eventClassMap = (Map<String, Object>) this.eventTypeMap
                .get(eventType);
        if (eventClassMap == null)
            return false;

        Map<String, Object> callbacksByCommandClass = (Map<String, Object>) eventClassMap
                .get(eventClass == null ? Event.class.hashCode() + ""
                        : eventClass.hashCode() + "");
        return callbacksByCommandClass != null && callbacksByCommandClass.get(commandClass.hashCode() + "") != null;

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #execute}
     */
    @Override
    public void execute(Class<?> commandClass, Object payload, Class<?> payloadClass, String named) {
        this.verifyCommandClass(commandClass);
        if ((payload != null) || (payloadClass != null)) {
            if (payloadClass == null) {
                payloadClass = this.reflector.getClass(payload);
            }
            if (Event.class.isInstance(payload)
                    && !payloadClass.equals(Event.class)) {
                this.injector.mapValue(Event.class, payload, "");
            }
            this.injector.mapValue(payloadClass, payload, named);
        }
        Object command = this.injector.instantiate(commandClass);
        if ((payload != null) || (payloadClass != null)) {
            if (Event.class.isInstance(payload)
                    && !payloadClass.equals(Event.class)) {
                this.injector.unmap(Event.class, "");
            }
            this.injector.unmap(payloadClass, named);
        }
        ((Command) command).execute();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #detain}
     */
    @Override
    public void detain(Object command) {
        this.detainedCommands.put(command.hashCode() + "", true);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.ICommandMap #release}
     */
    @Override
    public void release(Object command) {
        if (this.detainedCommands.get(command.hashCode() + "") != null) {
            this.detainedCommands.remove(command.hashCode() + "");
        }
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * @param commandClass commandClass
     * throws <code>com.camnter.robotlegs4android.base.ContextError</code>
     */
    protected void verifyCommandClass(Class<?> commandClass) {
        if (this.verifiedCommandClasses.get(commandClass.getName()) == null) {
            try {
                this.verifiedCommandClasses.put(commandClass.hashCode() + "",
                        commandClass.getMethod("execute").toString());
            } catch (NoSuchMethodException e) {
                String TAG = "CommandMap";
                Log.e(TAG, e.getMessage());
            }
            if (this.verifiedCommandClasses.get(commandClass.hashCode() + "") == null) {
                throw new ContextError(ContextError.E_COMMANDMAP_NOIMPL + " - "
                        + commandClass);
            }
        }
    }

    /**
     * MapEventListener Event Handler
     * MapEventListener 的事件处理方法
     *
     * @param event              The <code>Event</code> Event类型
     * @param commandClass       The Class to construct and execute 类来构建和执行
     * @param oneshot            Should this command mapping be removed after execution?
     *                           这个命令映射应该移除之后执行吗?
     * @param originalEventClass originalEventClass
     * @return <code>true</code> if the event was routed to a Command and the
     * Command was executed, <code>false</code> otherwise
     * true 如果事件被路由到一个命令和命令执行,否则false
     */
    protected Boolean routeEventToCommand(Event event, Class<?> commandClass, Boolean oneshot, Class<?> originalEventClass) {
        if (!(originalEventClass.isInstance(event)))
            return false;

        this.execute(commandClass, event, null, "");
        if (oneshot) {
            this.unmapEvent(event.getType(), commandClass, originalEventClass);
        }
        return true;
    }

    /**
     * Listens for mapEvent
     * 用于监听mapEvent
     *
     * @author CaMnter
     */
    private final class MapEventListener extends Listener {
        private final Class<?> _commandClass;
        private final Class<?> _eventClass;
        private final Boolean _oneshot;

        public MapEventListener(String type, String name, Class<?> commandClass, Class<?> eventClass, Boolean oneshot) {
            super(type, name);
            this._commandClass = commandClass;
            this._eventClass = eventClass;
            this._oneshot = oneshot;
        }

        @Override
        public void onHandle(Event event) {
            CommandMap.this.routeEventToCommand(event, this._commandClass,
                    this._oneshot, this._eventClass);
        }
    }
}
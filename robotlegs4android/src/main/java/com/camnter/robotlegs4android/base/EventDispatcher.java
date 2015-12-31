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
import com.camnter.robotlegs4android.core.IListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description：EventDispatcher
 * Created by：CaMnter
 */
public class EventDispatcher implements IEventDispatcher {

    private static Map<String, EventDispatcher> eventDispatchers = new HashMap<>();
    private final List<IListener> listeners = new ArrayList<>();

    /**
     * No arguments constructor
     * 无参构造函数
     */
    public EventDispatcher() {

    }

    /**
     * Need IEventDispatcher type argument
     * 需要IEventDispatcher类型参数
     *
     * @param target The IEventDispatcher type IEventDispatcher类型
     */
    public EventDispatcher(IEventDispatcher target) {

    }

    /**
     * Set a Dispatcher
     * 设置一个分发器
     *
     * @param name The dispatcher's name 分发器的名称
     * @return The dispatcher has been set 已经被设置的分发器
     */
    public static EventDispatcher setDispatcher(String name) {
        EventDispatcher dispatcher = new EventDispatcher();
        EventDispatcher.eventDispatchers.put(name, dispatcher);
        return dispatcher;
    }

    /**
     * According to the name of the dispatcher take out a dispatcher
     * 根据分发器的name取出一个分发器
     *
     * @param name The dispatcher's name 分发器的名称
     * @return Take out a dispenser 取出的一个分发器
     */
    public static EventDispatcher getDispatcher(String name) {
        return EventDispatcher.eventDispatchers.get(name);
    }

    /**
     * According to the name of the dispatcher to remove a dispatcher
     * 根据分发器的name删除一个分发器
     *
     * @param name The dispatcher's name 分发器的名称
     */
    public static void removeDispatcher(String name) {
        EventDispatcher.eventDispatchers.remove(name);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #addEventListener}
     */
    @Override
    public void addEventListener(String type, IListener listener, Boolean useCapture, int priority, Boolean useWeakReference) {
        listener.setType(type);
        this.listeners.add(listener);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #dispatchEvent}
     */
    @Override
    public Boolean dispatchEvent(Event event) {

        /*
         * Through this. All the listeners in the collection of listeners
         * 遍历this.listeners集合里所有的listeners
         */
        for (IListener listener : this.listeners) {

            /*
             * One judge whether the type of the listener and events in the
             * event type
             * 逐个判断listener的type是否与事件event里的type一致
             */
            if (listener.getType().equals(event.getType())) {

                /*
                 * In the agreement, to perform the listener onHandle (Event
                 * Event) method
                 * 一致则执行listener中的onHandle(Event event)方法
                 */
                listener.onHandle(event);
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #hasEventListener}
     */
    @Override
    public Boolean hasEventListener(String type) {

        /*
         * Through this. Listeners in the static set all the listeners
         * 遍历this.listeners静态集合里所有的listeners
         */
        for (IListener listener : this.listeners) {

            /*
             * One judge whether the type of the listener and events in the
             * event type
             * 逐个判断listener的type是否与type一致
             */
            if (listener.getType().equals(type)) {

            /*
             * In the agreement, to perform the listener onHandle (Event
             * Event) method
             * 一致则返回true
             */
                return true;
            }

        }

        /*
         * Does not agree, it returns false
         * 不一致则返回false
         */
        return false;

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #removeEventListener}
     */
    @Override
    public void removeEventListener(String type, IListener listener, Boolean useCapture) {
        Iterator<IListener> listenersIterator = this.listeners.iterator();

        /*
         * Through this. All the listeners in the collection of listeners
         * 遍历this.listeners集合里所有的listeners
         */
        while (listenersIterator.hasNext()) {
            IListener tempListener = listenersIterator.next();

            /*
             * Judge each type and the name of the listener
             * 判断每一个listener的type和name
             */
            if (tempListener.getType().equals(type) && tempListener.getName().equals(listener.getName())) {

                /*
                 * Through the Iterator. Remove () to delete the current the
                 * listener
                 * 通过Iterator.remove() 删除当前这个listener
                 */
                listenersIterator.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #willTrigger}
     */
    @Override
    public Boolean willTrigger(String type) {
        return true;
    }

}
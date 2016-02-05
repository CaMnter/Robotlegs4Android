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

package com.camnter.robotlegs4android.core;

/**
 * Description：IEventMap
 * Created by：CaMnter
 */
public interface IEventMap {

    /**
     * {@inheritDoc}
     * The same as calling <code>addEventListener</code> directly on the
     * <code>IEventDispatcher</code>, but keeps a list of listeners for easy
     * (usually automatic) removal.
     * 和调用IEventDispatcher里的addEventListener一样,但保持简单的侦听器列表(通常是自动)。
     *
     * @param dispatcher       The <code>IEventDispatcher</code> to listen to
     * @param type             The <code>Event</code> type to listen for
     * @param listener         The <code>Event</code> handler
     * @param eventClass       Optional Event class for a stronger mapping. Defaults to
     *                         <code>Event</code>. 可选事件类更强的映射。默认值为Event
     * @param useCapture       useCapture
     * @param priority         priority
     * @param useWeakReference useWeakReference
     */
    void mapListener(IEventDispatcher dispatcher, String type,
                     IListener listener, Class<?> eventClass, Boolean useCapture,
                     int priority, Boolean useWeakReference);

    /**
     * {@inheritDoc}
     * The same as calling <code>removeEventListener</code> directly on the
     * <code>IEventDispatcher</code>, but updates our local list of listeners.
     * 和调用IEventDispatcher里的removeEventListener一样,但是更新我们当地的侦听器列表。
     *
     * @param dispatcher The <code>IEventDispatcher</code>
     * @param type       The <code>Event</code> type
     * @param listener   The <code>Event</code> handler
     * @param eventClass Optional Event class for a stronger mapping. Defaults to
     *                   <code>Event</code>. 可选事件类更强的映射。默认值为Event。
     * @param useCapture useCapture
     */
    void unmapListener(IEventDispatcher dispatcher, String type,
                       IListener listener, Class<?> eventClass, Boolean useCapture);

    /**
     * {@inheritDoc}
     * Removes all listeners registered through <code>mapListener</code>
     * 通过mapListener删除所有侦听器注册
     */
    void unmapListeners();

}
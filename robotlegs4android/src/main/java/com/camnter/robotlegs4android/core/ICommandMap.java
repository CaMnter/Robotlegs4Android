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
 * Description：ICommandMap
 * Created by：CaMnter
 */
public interface ICommandMap {

    /**
     * {@inheritDoc}
     * Detain a Command instance
     * 拘留一个命令实例
     *
     * @param command The Command instance to detain 命令实例拘留
     */
    void detain(Object command);

    /**
     * {@inheritDoc}
     * Release a Command instance
     * 释放一个命令实例
     *
     * @param command The Command instance to release for garbage collection
     *                命令实例发布垃圾收集
     */
    void release(Object command);

    /**
     * {@inheritDoc}
     * Execute a Command with an optional payload
     * 执行一个命令和一个可选的有效负载
     * The <code>commandClass</code> must implement an execute()
     * method：Command类必须实现一个execute()方法
     *
     * @param commandClass The Class to instantiate - must have an execute() method
     *                     要实例化的类，必须有一个execute()方法
     * @param payload      An optional payload 一个可选的有效载荷
     * @param payloadClass An optional class to inject the payload as 一个可选的类注入有效负载
     * @param named        An optional name for the payload injection 一个可选的有效负载注入的名称
     */
    void execute(Class<?> commandClass, Object payload,
                 Class<?> payloadClass, String named);

    /**
     * {@inheritDoc}
     * Map a Class to an Event type
     * 一个类映射到一个事件类型
     * The <code>commandClass</code> must implement an execute()
     * method：Command类必须实现一个execute()方法
     *
     * @param eventType    The Event type to listen for 监听的事件类型
     * @param commandClass The Class to instantiate - must have an execute() method
     *                     要实例化的类，必须有一个execute()方法
     * @param eventClass   Optional Event class for a stronger mapping. Defaults to
     *                     <code>Event</code>. Your commandClass can optionally [Inject]
     *                     a variable of this type to access the event that triggered the
     *                     command 可选事件类更强的映射。默认为Event,你的commandClass可以可选(注入)
     *                     这种类型的一个变量来访问事件,引发了命令
     * @param oneshot      Unmap the Class after execution? 执行后取消类吗?
     */
    void mapEvent(String eventType, Class<?> commandClass,
                  Class<?> eventClass, Boolean oneshot);

    /**
     * {@inheritDoc}
     * Unmap a Class to Event type mapping
     * 取消一个类来事件类型映射
     *
     * @param eventType    The Event type 事件类型
     * @param commandClass The Class to unmap 要取消的类
     * @param eventClass   Optional Event class for a stronger mapping. Defaults to
     *                     <code>Event</code>. 可选事件类更强的映射。默认为Event
     */
    void unmapEvent(String eventType, Class<?> commandClass,
                    Class<?> eventClass);

    /**
     * Removes all mappings made through <code>mapEvent</code>
     * 通过mapEvent删除所有映射
     */
    void unmapEvents();

    /**
     * Check if a Class has been mapped to an Event type
     * 检查是否一个类被映射到一个事件类型
     *
     * @param eventType    The Event type 事件类型
     * @param commandClass The Class 这个类
     * @param eventClass   Optional Event class for a stronger mapping. Defaults to
     *                     <code>Event</code>. 可选事件类更强的映射。默认为Event
     * @return Whether the Class is mapped to this Event type 是否类映射到这个事件类型
     */
    Boolean hasEventCommand(String eventType, Class<?> commandClass,
                            Class<?> eventClass);

}
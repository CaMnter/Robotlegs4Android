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

import com.camnter.robotlegs4android.base.Event;

/**
 * Description：IEventDispatcher
 * Created by：CaMnter
 */
public interface IEventDispatcher {

    /**
     * 使用 EventDispatcher 对象注册事件侦听器对象，以使侦听器能够接收事
     * 件通知。可以为特定类型的事件、阶段和优先级在显示列表的所有节点上注 册事件侦听器。
     * 使用 EventDispatcher
     * 对象注册事件侦听器对象，以使侦听器能够接收事件通知。可以为特定类型的事件、阶段和优先级在显示列表的所有节点上注册事件侦听器。
     * 成功注册一个事件侦听器后，无法通过额外调用 addEventListener() 来更改其优先级。要更改侦听器的优先级，必须先调用
     * removeEventListener()。然后，可以使用新的优先级再次注册该侦听器。注册该侦听器后，如果继续调用具有不同 type 或
     * useCapture 值的 addEventListener()，则会创建单独的侦听器注册。例如，如果首先注册 useCapture 设置为
     * true 的侦听器，则该侦听器只在捕获阶段进行侦听。如果您使用同一个侦听器对象再次调用 addEventListener()，但
     * useCapture 设置为
     * false，则您将有两个单独的侦听器：一个在捕获阶段侦听，另一个在目标阶段和冒泡阶段侦听。不能只为目标阶段或冒泡阶段注册事件侦听器
     * 。这些阶段在注册期间是成对出现的，因为冒泡阶段只适用于目标节点的始祖。如果不再需要某个事件侦听器，可调用
     * EventDispatcher.removeEventListener()
     * 删除它；否则会产生内存问题。由于垃圾回收器不会删除仍包含引用的对象，因此不会从内存中自动删除使用已注册事件侦听器的对象。复制
     * EventDispatcher
     * 实例时并不复制其中附加的事件侦听器。（如果新近创建的节点需要一个事件侦听器，必须在创建该节点后附加该侦听器。）但是，如果移动
     * EventDispatcher
     * 实例，则其中附加的事件侦听器也会随之移动。如果在正在处理事件的节点上注册事件侦听器，则不会在当前阶段触发事件侦听器，
     * 但会在事件流的稍后阶段触发，如冒泡阶段
     * 。如果从正在处理事件的节点中删除事件侦听器，则该事件侦听器仍由当前操作触发。删除事件侦听器后，决不会再次调用该事件侦听器
     * （除非再次注册以备将来处理）。
     *
     * @param type             事件类型
     * @param listener         处理事件的侦听器函数。此函数必须接受事件对象作为其唯一的参数，并且不能返回任何结果，如下面的示例所示：
     *                         function(evt:Event):void 函数可以有任何名称。
     * @param useCapture       确定侦听器是运行于捕获阶段还是运行于目标和冒泡阶段。如果将 useCapture 设置为
     *                         true，则侦听器只在捕获阶段处理事件，而不在目标或冒泡阶段处理事件。如果 useCapture 为
     *                         false，则侦听器只在目标或冒泡阶段处理事件。要在所有三个阶段都侦听事件，请调用两次
     *                         addEventListener()，一次将 useCapture 设置为 true，第二次再将 useCapture
     *                         设置为 false。
     * @param priority         事件侦听器的优先级。优先级由一个 32 位整数指定。数字越大，优先级越高。优先级为 n 的所有侦听器会在优先级为 n-1
     *                         的侦听器之前处理。如果两个或更多个侦听器共享相同的优先级，则按照它们的添加顺序进行处理。默认优先级为 0。
     * @param useWeakReference 确定对侦听器的引用是强引用，还是弱引用。强引用（默认值）可防止您的侦听器被当作垃圾回收。弱引用则没有此作用。
     *                         类级别成员函数不属于垃圾回收的对象，因此可以对类级别成员函数将 useWeakReference 设置为 true
     *                         而不会使它们受垃圾回收的影响。如果对作为嵌套内部函数的侦听器将 useWeakReference 设置为
     *                         true，则该函数将被作为垃圾回收并且不再是永久函数
     *                         。如果创建对该内部函数的引用（将该函数保存到另一个变量中），则该函数将不作为垃圾回收并仍将保持永久。
     */
    void addEventListener(String type, IListener listener,
                          Boolean useCapture, int priority, Boolean useWeakReference);

    /**
     * {@inheritDoc}
     * 将事件调度到事件流中。事件目标是对其调用 dispatchEvent() 的 EventDispatcher 对象。
     *
     * @param event 调度到事件流中的事件对象。
     * @return 除非对事件调用 preventDefault()（在这种情况下，它返回 false），否则值为 true。
     */
    Boolean dispatchEvent(Event event);

    /**
     * {@inheritDoc}
     * 检查 EventDispatcher 对象是否为特定事件类型注册了任何侦听器。这样，就可以确定 EventDispatcher
     * 对象在事件流层次结构中的哪个位置改变了对事件类型的处理。要确定特定事件类型是否确实会触发事件侦听器，请使用
     * IEventDispatcher.willTrigger()。 hasEventListener() 与 willTrigger()
     * 的区别是：hasEventListener() 只检查它所属的对象，而 willTrigger() 检查整个事件流以查找由 type
     * 参数指定的事件。
     *
     * @param type 事件的类型。
     * @return 如果指定类型的侦听器已注册，则值为 true；否则，值为 false。
     */
    Boolean hasEventListener(String type);

    /**
     * {@inheritDoc}
     * 从 EventDispatcher 对象中删除侦听器。如果没有向 EventDispatcher
     * 对象注册任何匹配的侦听器，则对此方法的调用没有任何效果。
     *
     * @param type       事件的类型。
     * @param listener   要删除的侦听器对象。
     * @param useCapture 指出是为捕获阶段还是为目标和冒泡阶段注册了侦听器。如果为捕获阶段以及目标和冒泡阶段注册了侦听器，则需要对
     *                   removeEventListener() 进行两次调用才能将这两个侦听器删除：一次调用将 useCapture 设置为
     *                   true，另一次调用将 useCapture 设置为 false。
     */
    void removeEventListener(String type, IListener listener,
                             Boolean useCapture);

    /**
     * {@inheritDoc}
     * 检查是否用此 EventDispatcher 对象或其任何始祖为指定事件类型注册了事件侦听器。将指定类型的事件调度给此
     * EventDispatcher 对象或其任一后代时，如果在事件流的任何阶段触发了事件侦听器，则此方法返回 true。
     * hasEventListener() 与 willTrigger() 的区别是：hasEventListener() 只检查它所属的对象，而
     * willTrigger() 检查整个事件流以查找由 type 参数指定的事件。
     *
     * @param type 事件的类型。
     * @return 如果将会触发指定类型的侦听器，则值为 true；否则，值为 false。
     */
    Boolean willTrigger(String type);

}

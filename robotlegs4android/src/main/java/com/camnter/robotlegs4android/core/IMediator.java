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

package com.camnter.robotlegs4android.core;

/**
 * Description：IMediator
 * Created by：CaMnter
 */
public interface IMediator {

    /**
     * {@inheritDoc}
     * 注册前
     * Should be invoked by the <code>IMediatorMap</code> during
     * <code>IMediator</code> registration
     * 在IMediator注册时,应该被IMediatorMap调用。
     */
    void preRegister();

    /**
     * {@inheritDoc}
     * 注册时
     * Should be invoked by the <code>IMediator</code> itself when it is ready
     * to be interacted with
     * 在本身当它准备好了与之交互时,应该被IMediator调用。
     * Override and place your initialization code here
     * 覆盖,这里的初始化代码
     */
    void onRegister();

    /**
     * {@inheritDoc}
     * 删除前
     * Invoked when the <code>IMediator</code> has been removed by the
     * <code>IMediatorMap</code>
     * IMediator 被删除前，被IMediatorMap调用.
     */
    void preRemove();

    /**
     * {@inheritDoc}
     * 删除时
     * Should be invoked by the <code>IMediator</code> itself when it is ready
     * to for cleanup
     * 在本身当它准备好了清理时,应该被IMediator调用。
     * Override and place your cleanup code here
     * 覆盖,这里的初始化代码
     */
    void onRemove();

    /**
     * {@inheritDoc}
     * Get the <code>IMediator</code>'s view component
     * 取得IMediator的视图组件
     *
     * @return The view component 视图组件
     */
    Object getViewComponent();

    /**
     * {@inheritDoc}
     * Set the <code>IMediator</code>'s view component
     * 设置IMediator的视图组件
     *
     * @param viewComponent The view component 视图组件
     */
    void setViewComponent(Object viewComponent);

}

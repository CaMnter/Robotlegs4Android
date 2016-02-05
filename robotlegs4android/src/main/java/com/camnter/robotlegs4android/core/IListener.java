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
 * Description：IListener
 * Created by：CaMnter
 */
public interface IListener {
    /**
     * {@inheritDoc}
     * @return The name of the listener：listener的名称
     */
    String getName();

    /**
     * {@inheritDoc}
     * @return The type of the listener：listener的类型
     */
    String getType();

    /**
     * {@inheritDoc}
     * @param type
     *            set type of the listener 设置listener的类型
     */
    void setType(String type);

    /**
     * {@inheritDoc}
     * @param event
     *            <code>com.camnter.robotlegs4android.base.Event</code>
     */
    void onHandle(Event event);

}
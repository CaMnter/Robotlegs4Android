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

package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectionPoint
 * Created by：CaMnter
 */
public class InjectionPoint {

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param node     node
     * @param injector injector
     */
    public InjectionPoint(XML node, Injector injector) {
        this.initializeInjection(node);
    }

    /**
     * {@inheritDoc}
     * Apply the injection
     * 申请注入器
     *
     * @param target   target
     * @param injector injector
     * @return Object
     */
    public Object applyInjection(Object target, Injector injector) {
        return target;
    }

    /*******************************************************************************************
     * protected methods *
     *******************************************************************************************/
    /**
     * {@inheritDoc}
     * Initialize the injection
     * 实例化注入器
     *
     * @param node node
     */
    protected void initializeInjection(XML node) {
    }

}
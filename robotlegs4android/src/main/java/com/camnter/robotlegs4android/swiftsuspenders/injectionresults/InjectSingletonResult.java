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

package com.camnter.robotlegs4android.swiftsuspenders.injectionresults;

import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectSingletonResult
 * Created by：CaMnter
 */
public class InjectSingletonResult extends InjectionResult {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private final Class<?> m_responseType;
    private Object m_response;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param responseType responseType
     */
    public InjectSingletonResult(Class<?> responseType) {
        this.m_responseType = responseType;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     *
     * @param injector injector
     * @return Object
     */
    @Override
    public Object getResponse(Injector injector) {
        if (this.m_response == null) {
            this.m_response = this.createResponse(injector);
        }
        return this.m_response;
    }

    /*******************************************************************************************
     * private methods *
     *******************************************************************************************/
    /**
     * Create the Response
     * 创建响应
     *
     * @param injector injector
     * @return Object
     */
    private Object createResponse(Injector injector) {
        return injector.instantiate(this.m_responseType);
    }

}
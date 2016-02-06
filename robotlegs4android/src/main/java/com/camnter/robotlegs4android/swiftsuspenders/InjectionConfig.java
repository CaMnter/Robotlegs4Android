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

package com.camnter.robotlegs4android.swiftsuspenders;

import android.util.Log;

import com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult;

/**
 * Description：InjectionConfig
 * Created by：CaMnter
 */
public class InjectionConfig {

    /*******************************************************************************************
     * public properties *
     *******************************************************************************************/
    public Class<?> request;

    public String injectionName;

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private Injector m_injector;

    private InjectionResult m_result;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param request       request
     * @param injectionName injectionName
     */
    public InjectionConfig(Class<?> request, String injectionName) {

        this.request = request;

        this.injectionName = injectionName;

    }

    /**
     * Get the response
     * 获得响应
     *
     * @param injector injector
     * @return Object
     */
    public Object getResponse(Injector injector) {
        if (this.m_result != null) {
            return this.m_result
                    .getResponse(this.m_injector != null ? this.m_injector
                            : injector);
        }
        InjectionConfig parentConfig = (this.m_injector != null ? this.m_injector
                : injector)
                .getAncestorMapping(this.request, this.injectionName);
        if (parentConfig != null)
            return parentConfig.getResponse(injector);

        return null;
    }

    /**
     * Determine whether there was a response
     * 判断是否有响应
     *
     * @param injector injector
     * @return Boolean
     */
    public Boolean hasResponse(Injector injector) {
        if (this.m_result != null)
            return true;

        InjectionConfig parentConfig = (this.m_injector != null ? this.m_injector
                : injector)
                .getAncestorMapping(this.request, this.injectionName);
        return parentConfig != null;
    }

    /**
     * Determine whether there was a own response
     * 判断是否有自己的响应
     *
     * @return Boolean
     */
    public Boolean hasOwnResponse() {
        return this.m_result != null;
    }

    /**
     * Set the result(this.m_result)
     * 设置this.m_result
     *
     * @param result result
     */
    public void setResult(InjectionResult result) {
        if ((this.m_result != null) && (result != null)) {
            Log.w("InjectionConfig",
                    "Warning: Injector already has a rule for type \""
                            + this.request.getName()
                            + "\", named \""
                            + this.injectionName
                            + "\".\n "
                            + "If you have overwritten this mapping intentionally you can use "
                            + "\"injector.unmap()\" prior to your replacement mapping in order to "
                            + "avoid seeing this message.");
        }
        this.m_result = result;
    }

    /**
     * Set the injector(this.m_injector)
     * 设置this.m_injector
     *
     * @param injector injector
     */
    public void setInjector(Injector injector) {
        this.m_injector = injector;
    }

}

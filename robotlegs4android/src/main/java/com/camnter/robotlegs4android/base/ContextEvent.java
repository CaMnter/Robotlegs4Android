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

/**
 * Description：ContextEvent
 * Created by：CaMnter
 */
public class ContextEvent extends Event {

    /***********************************
     * Startup
     ****************************************/

    public static final String STARTUP = "startup";

    public static final String STARTUP_COMPLETE = "startupComplete";

    /***********************************
     * Shutdown
     ***************************************/

    public static final String SHUTDOWN = "shutdown";

    public static final String SHUTDOWN_COMPLETE = "shutdownComplete";

    /************************************************************************************/

    /**
     * @param type type
     */
    public ContextEvent(String type) {
        super(type);
    }

    /**
     * Get The Event Type
     * 取得事件类型
     *
     * @return The Event Type 事件类型
     */
    public String getEventType() {
        return this.getType();
    }

}
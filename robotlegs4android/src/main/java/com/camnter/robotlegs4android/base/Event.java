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
 * Description：Event
 * Created by：CaMnter
 */
public class Event {

    public static final String ADDED_TO_STAGE = "added_to_stage";
    public static final String REMOVED_FROM_STAGE = "removed_from_stage";
    public static final String ENTER_FRAME = "enter_frame";

    private String _type;
    private Object _target = null;
    public Object data;

    public Event(String type) {
        this._type = type;
    }

    public Event(String type, Boolean bubble, Boolean cancelable) {
    }

    public String getType() {
        return this._type;
    }

    public Object getTarget() {
        return this._target;
    }

    public void setTarget(Object target) {
        this._target = target;
    }

}
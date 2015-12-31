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

package com.camnter.robotlegs4android.test.event;

import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.test.bean.User;

/**
 * Description：LoginEvent
 * Created by：CaMnter
 * Time：2015-11-07 23:05
 */
public class LoginEvent extends Event {

    public static final String USER_LOGIN = "user_login";
    public static final String USER_LOGOUT = "user_logout";

    public static final String USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER = "user_login_success_from_model_to_controller";
    public static final String USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW = "user_login_success_from_model_to_view";
    public static final String USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW = "user_login_success_from_controller_to_view";

    public String name;
    public String password;

    public User user;

    public LoginEvent(String type) {
        super(type);
    }

}

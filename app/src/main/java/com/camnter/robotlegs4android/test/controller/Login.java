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

package com.camnter.robotlegs4android.test.controller;

import android.util.Log;

import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.mvcs.Command;
import com.camnter.robotlegs4android.test.event.LoginEvent;
import com.camnter.robotlegs4android.test.model.UserModel;

/**
 * Description：Login
 * Created by：CaMnter
 * Time：2015-11-07 22:58
 */
public class Login extends Command {

    private static final String TAG = "Login";

    @Inject
    public UserModel userModel;

    @Inject
    public LoginEvent event;


    /**
     * TODO - The Command subclass must inherit the execute method
     * 备忘录 - Command子类必须继承execute方法
     */
    @Override
    public void execute() {
        switch (event.getType()) {
            case LoginEvent.USER_LOGIN: {
                userModel.login(event.name, event.password);
                break;
            }
            case LoginEvent.USER_LOGOUT: {
                userModel.logout();
                break;
            }
            case LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER: {
                Log.i(TAG, "This Login Controller know the user login success");

                /*
                 * send an USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW type of event to View layer
                 * 发送一个USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的事件到View层
                 */
                this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW));
                break;
            }
        }
    }
}

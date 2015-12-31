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

package com.camnter.robotlegs4android.test.model;

import com.camnter.robotlegs4android.mvcs.Actor;
import com.camnter.robotlegs4android.test.bean.User;
import com.camnter.robotlegs4android.test.event.LoginEvent;

/**
 * Description：UserModel
 * Created by：CaMnter
 * Time：2015-11-07 22:58
 */
public class UserModel extends Actor {

    public void login(String name,String password) {

        // TODO Do you want to network requests

        User user = new User();
        user.name = "CaMnter";
        user.sign = "Save you from anything";

        /*
         * you can send a your custom event from Model layer to View layer
         * 你可以发送一个你自定义的事件从Model层到View层
         */
        LoginEvent loginEvent = new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW);
        loginEvent.user = user;
        this.dispatch(loginEvent);
        this.dispatch(new LoginEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER));
    }

    public boolean logout(){

        // TODO Do you want to network requests

        return true;
    }


}

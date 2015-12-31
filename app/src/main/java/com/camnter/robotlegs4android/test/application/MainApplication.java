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

package com.camnter.robotlegs4android.test.application;

import com.camnter.robotlegs4android.mvcs.Context;
import com.camnter.robotlegs4android.test.robotlegscontext.MainContext;
import com.camnter.robotlegs4android.views.RobotlegsApplication;

/**
 * Description：MainApplication
 * Created by：CaMnter
 * Time：2015-11-08 23:22
 */
public class MainApplication extends RobotlegsApplication {

    private static MainApplication ourInstance = new MainApplication();

    public static MainApplication getInstance() {
        return ourInstance;
    }


    /**
     * Please write your custom robotlegs4android context
     * 请填写你自定义的robotlegs4android context
     * TODO After write your custom robotlegs4android context, please don't call this method
     * TODO 填写完你自定义的robotlegs4android context后，请不要调用此方法
     *
     * @return
     */
    @Override
    protected Context getMvcContextInstance() {
        return new MainContext(this, true);
    }

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (ourInstance == null) ourInstance = this;
    }

}

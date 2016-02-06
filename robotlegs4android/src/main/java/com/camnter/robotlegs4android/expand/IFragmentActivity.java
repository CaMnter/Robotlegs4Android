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

package com.camnter.robotlegs4android.expand;

import android.view.KeyEvent;

import com.camnter.robotlegs4android.core.IListener;

/**
 * Description：IContextActivity
 * Created by：CaMnter
 */
public interface IFragmentActivity {

    /**
     * Through monitoring the back key, management robotlegs4android into fragments
     * 通过监听回退键，管理robotlegs4android注入的Fragment
     *
     * @param event event
     * @return boolean
     */
    boolean goBack(KeyEvent event);

    /**
     * 获取robotlegs4android的回退键listener
     * Get the robotlegs4android's back click listener
     *
     * @return IListener
     */
    IListener getOnBackClickListener();

    /**
     * robotlegs4android的listener替换android回退建的listener
     * robotlegs4android's listener replace to the android's back listener
     *
     * @param listener listener
     */
    void replaceOnBackClickListener(IListener listener);

}

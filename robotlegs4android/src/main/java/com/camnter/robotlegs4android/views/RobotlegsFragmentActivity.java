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

package com.camnter.robotlegs4android.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.base.EventDispatcher;
import com.camnter.robotlegs4android.base.Listener;
import com.camnter.robotlegs4android.core.IListener;
import com.camnter.robotlegs4android.expand.IApplication;
import com.camnter.robotlegs4android.expand.IFragmentActivity;

/**
 * Description：RobotlegsFragmentActivity
 * Created by：CaMnter
 * Time：2015-11-08 00:44
 */
public abstract class RobotlegsFragmentActivity extends AppCompatActivity implements IFragmentActivity {

    /**
     * Define a robotlegs4android 's listener, used to cover the Android back button to the listener
     * 定义一个robotlegs4android's listener,用于覆盖Android回退键listener
     */
    private IListener onBackClickListener;

    private static final String EVENT_TYPE_ON_BACK_CLICK = "event_type_on_back_click";

    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return int
     */
    public abstract int getLayoutId();

    /**
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) this.setContentView(this.getLayoutId());
        /*
         * inject the activity's mediator when it on create
         * 在 onCreate 的时候 注入这个Activity的mediator
         */
        try {
            ((IApplication) this.getApplication()).getMvcContext().injectMediator(this);
            EventDispatcher.setDispatcher(this.getClass().getSimpleName() + this.hashCode() + "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Please create a custom RobotlegsApplication and fill in the getMvcContextInstance() method");
        }
    }

    @Override
    protected void onDestroy() {
        /*
         * if the activity on destroy and it's mediator will be removed
         * 如果activity将被销毁，那么它的mediator也将被移除
         */
        try {
            ((IApplication) this.getApplication()).getMvcContext().removeMediator(this);
            EventDispatcher.removeDispatcher(this.getClass().getSimpleName() + this.hashCode() + "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Please create a custom RobotlegsApplication and fill in the getMvcContextInstance() method");
        }
        super.onDestroy();
    }

    /**
     * Through monitoring the back key, management robotlegs4android into fragments
     * 通过监听回退键，管理robotlegs4android注入的Fragment
     *
     * @param event event
     * @return boolean
     */
    @Override
    public boolean goBack(KeyEvent event) {
        /**
         * 如果全部Fragment个数为0，才可以finish。否则，继续
         * if the all fragment count == 0
         */
        if (this.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.finish();
        } else {
            super.onKeyDown(KeyEvent.KEYCODE_BACK, event);
        }
        return true;
    }

    /**
     * 获取robotlegs4android的回退键listener
     * Get the robotlegs4android's back click listener
     *
     * @return IListener
     */
    @Override
    public IListener getOnBackClickListener() {
        return this.onBackClickListener;
    }

    /**
     * robotlegs4android的listener替换android回退建的listener
     * robotlegs4android's listener replace to the android's back listener
     *
     * @param listener listener
     */
    @Override
    public void replaceOnBackClickListener(IListener listener) {
        this.onBackClickListener = listener == null ? new Listener("", "") {
            @Override
            public void onHandle(Event event) {
                RobotlegsFragmentActivity.this.goBack((KeyEvent) event.data);
            }
        } : listener;
    }

    /**
     * Take care of calling onBackPressed() for pre-Eclair platforms.
     *
     * @param keyCode keyCode
     * @param event   event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getRepeatCount() == 0)) {
            /*
             * If there is no a Mediator
             * 如果不存在一个Mediator
             */
            if (this.getOnBackClickListener() == null) {
                this.goBack(event);
            } else {
                this.getOnBackClickListener().onHandle(new Event(EVENT_TYPE_ON_BACK_CLICK) {
                    {
                    /*
                     * Inform robotlegs4android back the listener to deal with this event.
                     * And will the KeyEvent to pass down as the data.
                     * 通知robotlegs4android的回退listener处理这个事件。
                     * 并且将这个KeyEvent作为数据传递下去。
                     */
                        this.data = event;
                    }
                });
            }
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

}

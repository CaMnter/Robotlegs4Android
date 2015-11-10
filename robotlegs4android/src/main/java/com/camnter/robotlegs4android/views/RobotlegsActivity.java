package com.camnter.robotlegs4android.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.camnter.robotlegs4android.base.EventDispatcher;
import com.camnter.robotlegs4android.expand.IApplication;

/**
 * Description：RobotlegsActivity
 * Created by：CaMnter
 * Time：2015-11-08 01:24
 */
public abstract class RobotlegsActivity extends AppCompatActivity {

    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return int
     */
    public abstract int getLayoutId();

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
            throw new RuntimeException("Please create a custom RobotlegsApplication");
        }
    }

    @Override
    protected void onDestroy() {
        /*
         * if the activity on destroy and it's mediator and listeners will be removed
         * 如果activity将被销毁，那么它的mediator和listener也将被移除
         */
        try {
            EventDispatcher.removeDispatcher(this.getClass().getSimpleName() + this.hashCode() + "");
            ((IApplication) this.getApplication()).getMvcContext().removeMediator(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Please create a custom RobotlegsApplication");
        }
        super.onDestroy();
    }

}

package com.camnter.robotlegs4android.test.application;

import com.camnter.robotlegs4android.mvcs.Context;
import com.camnter.robotlegs4android.test.robotlegscontext.MainContext;
import com.camnter.robotlegs4android.views.RobotlegsApplication;

/**
 * Description：
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

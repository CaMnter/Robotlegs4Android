package com.camnter.robotlegs4android.views;

import android.app.Application;

import com.camnter.robotlegs4android.expand.IApplication;
import com.camnter.robotlegs4android.mvcs.Context;

/**
 * Description：RobotlegsApplication
 * Created by：CaMnter
 * Time：2015-11-08 23:30
 */
public abstract class RobotlegsApplication extends Application implements IApplication {

    /**
     * robotlegs4android's context
     * robotlegs4android的context
     */
    public Context context;

    /**
     * Get the robotlegs4android's context
     * 获取robotlegs4android的context
     */
    @Override
    public Context getMvcContext() {
        if (this.context == null)
            this.context = this.getMvcContextInstance();

        return this.context;
    }

    /**
     * Please write your custom robotlegs4android context
     * 请填写你自定义的robotlegs4android context
     * <p/>
     * TODO After write your custom robotlegs4android context, please don't call this method
     * TODO 填写完你自定义的robotlegs4android context后，请不要调用此方法
     *
     * @return
     */
    protected abstract Context getMvcContextInstance();

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
        this.getMvcContext();
    }

}

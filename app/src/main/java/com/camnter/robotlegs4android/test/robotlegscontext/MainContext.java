package com.camnter.robotlegs4android.test.robotlegscontext;

import com.camnter.robotlegs4android.mvcs.Context;
import com.camnter.robotlegs4android.test.controller.Login;
import com.camnter.robotlegs4android.test.event.LoginEvent;
import com.camnter.robotlegs4android.test.model.UserModel;
import com.camnter.robotlegs4android.test.view.activity.MainActivity;
import com.camnter.robotlegs4android.test.view.activity.MainActivityMediator;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutFirstFragment;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutFirstFragmentMediator;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutFourthFragment;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutFourthFragmentMediator;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutSecondFragment;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutSecondFragmentMediator;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutThirdFragment;
import com.camnter.robotlegs4android.test.view.fragment.TabLayoutThirdFragmentMediator;

/**
 * Description：MainContext
 * Created by：CaMnter
 * Time：2015-11-06 16:22
 */
public class MainContext extends Context {

    public MainContext(Object contextView, Boolean autoStartup) {
        super(contextView, autoStartup);
    }

    /**
     * set your mvc relation
     * 设置你的mvc关系
     * <p/>
     * Add the view map
     * Link the View and View the corresponding Mediator
     * 添加view映射
     * 将View 和 View 对应的 Mediator 联系起来
     * <p/>
     * Injection as an singleton, instantiate the singleton
     * 注入实例，实例化单例
     * <p/>
     * Add Event (Event) with the connection of the Command
     * 添加事件（Event）与Command的联系
     */
    @Override
    public void setMvcRelation() {

        /*
         * view映射
         * 将View 和 View 对应的 Mediator 联系起来
         * Add the view map
         * Link the View and View the corresponding Mediator
         */
        this.getMediatorMap().mapView(MainActivity.class, MainActivityMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutFirstFragment.class, TabLayoutFirstFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutSecondFragment.class, TabLayoutSecondFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutThirdFragment.class, TabLayoutThirdFragmentMediator.class, null, true, true);
        this.getMediatorMap().mapView(TabLayoutFourthFragment.class, TabLayoutFourthFragmentMediator.class, null, true, true);

        /*
         * 注入实现 实例化单例
         * Injection as an singleton, instantiate the singleton
         */
        this.getInjector().mapSingleton(UserModel.class, "");

        /*
         * 添加事件与Command的联系
         * Add Event (Event) with the connection of the Command
         */
        this.getCommandMap().mapEvent(LoginEvent.USER_LOGIN, Login.class,
                null, false);
        this.getCommandMap().mapEvent(LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_CONTROLLER, Login.class, null, false);

    }


}
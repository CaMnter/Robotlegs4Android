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
     * @param event
     * @return
     */
    boolean goBack(KeyEvent event);

    /**
     * 获取robotlegs4android的回退键listener
     * Get the robotlegs4android's back click listener
     *
     * @return
     */
    IListener getOnBackClickListener();

    /**
     * robotlegs4android的listener替换android回退建的listener
     * robotlegs4android's listener replace to the android's back listener
     *
     * @param listener
     */
    void replaceOnBackClickListener(IListener listener);

}

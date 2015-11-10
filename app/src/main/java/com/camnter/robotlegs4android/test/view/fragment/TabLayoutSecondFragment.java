package com.camnter.robotlegs4android.test.view.fragment;

import com.camnter.robotlegs4android.test.R;
import com.camnter.robotlegs4android.views.RobotlegsFragment;


/**
 * Description：TabLayoutSecondFragment
 * Created by：CaMnter
 * Time：2015-10-17 12:15
 */
public class TabLayoutSecondFragment extends RobotlegsFragment {

    private static TabLayoutSecondFragment instance;

    private TabLayoutSecondFragment() {
    }

    public static TabLayoutSecondFragment getInstance() {
        if (instance == null) instance = new TabLayoutSecondFragment();
        return instance;
    }


    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.tablayout_second_fragment;
    }
}

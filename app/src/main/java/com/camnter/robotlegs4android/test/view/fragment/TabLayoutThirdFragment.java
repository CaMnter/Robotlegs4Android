package com.camnter.robotlegs4android.test.view.fragment;

import com.camnter.robotlegs4android.test.R;
import com.camnter.robotlegs4android.views.RobotlegsFragment;


/**
 * Description：
 * Created by：CaMnter
 * Time：2015-10-17 12:15
 */
public class TabLayoutThirdFragment extends RobotlegsFragment {

    private static TabLayoutThirdFragment instance;

    private TabLayoutThirdFragment() {
    }

    public static TabLayoutThirdFragment getInstance() {
        if (instance == null) instance = new TabLayoutThirdFragment();
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
        return R.layout.tablayout_third_fragment;
    }
    
}

package com.camnter.robotlegs4android.test.view.fragment;

import com.camnter.robotlegs4android.test.R;
import com.camnter.robotlegs4android.views.RobotlegsFragment;


/**
 * Description：TabLayoutFirstFragment
 * Created by：CaMnter
 * Time：2015-10-17 12:15
 */
public class TabLayoutFirstFragment extends RobotlegsFragment {

    private static TabLayoutFirstFragment instance;

    private TabLayoutFirstFragment() {
    }

    public static TabLayoutFirstFragment getInstance() {
        if (instance == null) instance = new TabLayoutFirstFragment();
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
        return R.layout.tablayout_first_fragment;
    }

}

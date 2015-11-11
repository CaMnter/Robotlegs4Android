package com.camnter.robotlegs4android.test.view.fragment;

import com.camnter.robotlegs4android.test.R;
import com.camnter.robotlegs4android.views.RobotlegsFragment;


/**
 * Description：TabLayoutFourthFragment
 * Created by：CaMnter
 * Time：2015-10-17 12:15
 */
public class TabLayoutFourthFragment extends RobotlegsFragment {

    private static TabLayoutFourthFragment instance;

    private TabLayoutFourthFragment() {
    }

    public static TabLayoutFourthFragment getInstance() {
        if (instance == null) instance = new TabLayoutFourthFragment();
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
        return R.layout.tablayout_fourth_fragment;
    }

}

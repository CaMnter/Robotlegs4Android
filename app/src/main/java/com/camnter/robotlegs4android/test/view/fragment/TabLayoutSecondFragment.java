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

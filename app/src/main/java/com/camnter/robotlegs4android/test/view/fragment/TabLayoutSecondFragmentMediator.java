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

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.base.Listener;
import com.camnter.robotlegs4android.core.IListener;
import com.camnter.robotlegs4android.core.IMediator;
import com.camnter.robotlegs4android.mvcs.Mediator;
import com.camnter.robotlegs4android.test.R;
import com.camnter.robotlegs4android.test.event.LoginEvent;
import com.camnter.robotlegs4android.test.widget.decorator.DividerItemDecoration;

import java.util.LinkedList;
import java.util.List;

/**
 * Description：TabLayoutSecondFragmentMediator
 * Created by：CaMnter
 * Time：2015-11-09 14:39
 */
public class TabLayoutSecondFragmentMediator extends Mediator {

    private TabLayoutSecondFragment fragment;

    private RecyclerView secondRV;

    /**
     * {@inheritDoc}
     * {@linkplain IMediator #onRegister}
     */
    @Override
    public void onRegister() {
        super.onRegister();
        this.fragment = (TabLayoutSecondFragment) this.getViewComponent();
        this.initViews();
        this.initListeners();
    }

    private void initViews() {
        this.secondRV = (RecyclerView) this.fragment.self.findViewById(R.id.second_rv);
        SecondRecyclerViewAdapter adapter = new SecondRecyclerViewAdapter();
        List<Integer> resIds = new LinkedList<>();
        resIds.add(R.mipmap.mm_1);
        resIds.add(R.mipmap.mm_2);
        resIds.add(R.mipmap.mm_3);
        this.secondRV.setAdapter(adapter);
        adapter.setList(resIds);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.fragment.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.secondRV.setLayoutManager(linearLayoutManager);
        this.secondRV.setItemAnimator(new DefaultItemAnimator());
        this.secondRV.addItemDecoration(new DividerItemDecoration(this.fragment.getActivity(), DividerItemDecoration.VERTICAL_LIST));
        this.secondRV.setHasFixedSize(true);
    }

    private void initListeners() {
         /*
         * listening your custom event（such as listening to an USER_LOGIN_SUCCESS type of LoginEvent）
         * listening from Controller layer to View layer in here
         * 监听你的自定义事件（例如监听一个USER_LOGIN_SUCCESS_FROM_CONTROLLER_TO_VIEW类型的LoginEvent）
         * 在这里监听从Controller层到View层
         */
        this.getEventMap().mapListener(this.getEventDispatcher(), LoginEvent.USER_LOGIN_SUCCESS_FROM_MODEL_TO_VIEW, new Listener() {
            /**
             * {@inheritDoc}
             * <p/>
             * {@linkplain IListener #onHandle}
             *
             * @param event
             */
            @Override
            public void onHandle(Event event) {
                if (event instanceof LoginEvent) {
                    TabLayoutSecondFragmentMediator.this.secondRV.setVisibility(View.VISIBLE);
                }
            }
        }, null, false, 0, true);
    }

    public class SecondRecyclerViewAdapter extends EasyRecyclerViewAdapter {

        @Override
        public int[] getItemLayouts() {
            return new int[]{R.layout.item_second_recycler};
        }

        @Override
        public void onBindRecycleViewHolder(EasyRecyclerViewHolder easyRecyclerViewHolder, int i) {
            int resId = (int) this.getList().get(i);
            ImageView secondIV = easyRecyclerViewHolder.findViewById(R.id.second_recycler_iv);
            secondIV.setImageResource(resId);
        }

        @Override
        public int getRecycleViewItemType(int i) {
            return 0;
        }

    }

}

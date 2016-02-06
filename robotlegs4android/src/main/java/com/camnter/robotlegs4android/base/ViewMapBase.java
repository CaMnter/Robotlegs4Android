/*
 * Copyright (C) 2015 CaMnter yuanyu.camnter@gmail.com
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

package com.camnter.robotlegs4android.base;

import com.camnter.robotlegs4android.core.IInjector;

/**
 * Description：ViewMapBase
 * Created by：CaMnter
 */
public class ViewMapBase {

    /**
     * private
     */
    protected Boolean _enabled = true;

    /**
     * private
     */
    protected Object _contextView;

    /**
     * private
     */
    protected IInjector injector;

    /**
     * private
     */
    protected Boolean useCapture;

    /**
     * private
     */
    protected int viewListenerCount;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * Creates a new <code>ViewMap</code> object
     * 创建一个新的ViewMap对象
     *
     * @param contextView The root view node of the context. The map will listen for
     *                    ADDED_TO_STAGE events on this node 上下文的根节点视图。
     *                    地图将在这个节点监听ADDED_TO_STAGE事件
     * @param injector    An <code>IInjector</code> to use for this context
     *                    这种情况下的IInjector使用
     */
    public ViewMapBase(Object contextView, IInjector injector) {
        this.injector = injector;

		/*
         * change this at your peril lest yet understand the problem and have a
		 * better solution
		 *
		 * 改变这种危险以免然而理解问题和有一个更好的解决方案
		 */
        this.useCapture = true;
        // this must come last, see the setter
        this.setContextView(contextView);
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * Get the contextView
     * {@inheritDoc}
     *
     * @return contextView
     */
    public Object getContextView() {
        return this._contextView;
    }

    /**
     * Set the contextView
     * {@inheritDoc}
     *
     * @param value The view that set the this._contextView
     */
    public void setContextView(Object value) {
        if (value != this._contextView) {
            this.removeListeners();
            this._contextView = value;
            if (this.viewListenerCount > 0) {
                this.addListeners();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return enabled
     */
    public Boolean getEnabled() {
        return this._enabled;
    }

    /**
     * {@inheritDoc}
     *
     * @param value enabled
     */
    public void setEnabled(Boolean value) {
        if (value != this._enabled) {
            this.removeListeners();
            this._enabled = value;
            if (this.viewListenerCount > 0) {
                this.addListeners();
            }
        }
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * Add the listeners
     * 添加listeners时
     * {@inheritDoc}
     *
     * private
     */
    protected void addListeners() {

    }

    /**
     * {@inheritDoc}
     * Remove the listeners
     * 删除listeners时
     *
     * private
     */
    protected void removeListeners() {

    }

    /**
     * {@inheritDoc}
     * when the view be added
     * 当view被添加时
     *
     * private
     */
    protected void onViewAdded(Event e) {

    }

}
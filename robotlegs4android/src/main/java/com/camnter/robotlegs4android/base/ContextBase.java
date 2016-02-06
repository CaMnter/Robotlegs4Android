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

import com.camnter.robotlegs4android.core.IContext;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IListener;

/**
 * Description：ContextBase
 * Created by：CaMnter
 */
public class ContextBase implements IContext, IEventDispatcher {

    private final IEventDispatcher _eventDispatcher;

    public ContextBase() {
        this._eventDispatcher = new EventDispatcher(this);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #addEventListener}
     */
    @Override
    public void addEventListener(String type, IListener listener, Boolean useCapture, int priority, Boolean useWeakReference) {
        this._eventDispatcher.addEventListener(type, listener, useCapture,
                priority, false);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #dispatchEvent}
     */
    @Override
    public Boolean dispatchEvent(Event event) {
        if (this._eventDispatcher.hasEventListener(event.getType()))
            return this._eventDispatcher.dispatchEvent(event);

        return false;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #hasEventListener}
     */
    @Override
    public Boolean hasEventListener(String type) {
        return this._eventDispatcher.hasEventListener(type);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #removeEventListener}
     */
    @Override
    public void removeEventListener(String type, IListener listener, Boolean useCapture) {
        this._eventDispatcher.removeEventListener(type, listener, useCapture);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IEventDispatcher #willTrigger}
     */
    @Override
    public Boolean willTrigger(String type) {

        return this._eventDispatcher.willTrigger(type);

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IContext #getEventDispatcher}
     */
    @Override
    public IEventDispatcher getEventDispatcher() {
        return this._eventDispatcher;
    }

}
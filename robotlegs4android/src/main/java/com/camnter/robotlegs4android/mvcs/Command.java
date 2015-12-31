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

package com.camnter.robotlegs4android.mvcs;

import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.core.ICommandMap;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.core.IMediatorMap;

/**
 * Description：Command
 * Created by：CaMnter
 */
public abstract class Command {

    @Inject
    public Object contextView;

    @Inject
    public ICommandMap commandMap;

    @Inject
    public IEventDispatcher eventDispatcher;

    @Inject
    public IInjector injector;

    @Inject
    public IMediatorMap mediatorMap;

    public Command() {

    }

    /**
     * TODO - The Command subclass must inherit the execute method
     * 备忘录 - Command子类必须继承execute方法
     */
    public abstract void execute();

    /**
     * Dispatch helper method
     * 调度辅助方法
     *
     * @param event The <code>Event</code> to dispatch on the
     *              <code>IContext</code>'s <code>IEventDispatcher</code>
     *              Event分派IContext的IEventDispatcher
     * @return Boolean
     */
    protected Boolean dispatch(Event event) {
        if (this.eventDispatcher.hasEventListener(event.getType()))
            return this.eventDispatcher.dispatchEvent(event);

        return false;
    }

}
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
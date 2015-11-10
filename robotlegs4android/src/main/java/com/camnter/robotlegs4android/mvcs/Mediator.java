package com.camnter.robotlegs4android.mvcs;

import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.base.EventMap;
import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.base.MediatorBase;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IEventMap;
import com.camnter.robotlegs4android.core.IListener;
import com.camnter.robotlegs4android.core.IMediatorMap;

/**
 * Description：Mediator
 * Created by：CaMnter
 */
public class Mediator extends MediatorBase {

    @Inject
    public Object contextView;

    @Inject
    public IMediatorMap mediatorMap;

    /**
     * @private
     */
    protected IEventDispatcher _eventDispatcher;

    /**
     * @private
     */
    protected IEventMap _eventMap;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    public Mediator() {

    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.base.MediatorBase #preRegister}
     */
    @Override
    public void preRegister() {
        super.preRegister();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.base.MediatorBase #preRemove}
     */
    @Override
    public void preRemove() {
        if (this._eventMap != null) {
            this._eventMap.unmapListeners();
        }
        super.preRemove();
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * @return <code>com.camnter.robotlegs4android.core.IEventDispatcher</code>
     */
    public IEventDispatcher getEventDispatcher() {
        return this._eventDispatcher;
    }

    /**
     * @param value <code>com.camnter.robotlegs4android.core.IEventDispatcher</code>
     */
    @Inject
    public void setEventDispatcher(IEventDispatcher value) {
        this._eventDispatcher = value;
    }

    /**
     * Local EventMap
     *
     * @return The EventMap for this Actor
     */
    protected IEventMap getEventMap() {
        if (this._eventMap == null) {
            this._eventMap = new EventMap(this.getEventDispatcher());
        }
        return this._eventMap;
    }

    /**
     * Dispatch helper method
     * 调度辅助方法
     *
     * @param event The Event to dispatch on the <code>IContext</code>'s
     *              <code>IEventDispatcher</code> Event分派IContext的IEventDispatcher
     * @return
     */
    protected Boolean dispatch(Event event) {
        if (this.getEventDispatcher().hasEventListener(event.getType()))
            return this.getEventDispatcher().dispatchEvent(event);

        return false;
    }

    /**
     * Syntactical sugar for mapping a listener to the
     * <code>viewComponent</code>
     *
     * @param type
     * @param listener
     * @param eventClass
     * @param useCapture
     * @param priority
     * @param useWeakReference
     */
    protected void addViewListener(String type, IListener listener,
                                   Class<?> eventClass, Boolean useCapture, int priority,
                                   Boolean useWeakReference) {
        this.getEventMap().mapListener((IEventDispatcher) this.viewComponent,
                type, listener, eventClass, useCapture, priority,
                useWeakReference);
    }

    /**
     * Syntactical sugar for mapping a listener from the
     * <code>viewComponent</code>
     *
     * @param type
     * @param listener
     * @param eventClass
     * @param useCapture
     */
    protected void removeViewListener(String type, IListener listener,
                                      Class<?> eventClass, Boolean useCapture) {
        this.getEventMap().unmapListener((IEventDispatcher) this.viewComponent,
                type, listener, eventClass, useCapture);
    }

    /**
     * Syntactical sugar for mapping a listener to an
     * <code>IEventDispatcher</code>
     *
     * @param type
     * @param listener
     * @param eventClass
     * @param useCapture
     * @param priority
     * @param useWeakReference
     */
    protected void addContextListener(String type, IListener listener,
                                      Class<?> eventClass, Boolean useCapture, int priority,
                                      Boolean useWeakReference) {
        this.getEventMap().mapListener(this.getEventDispatcher(), type,
                listener, eventClass, useCapture, priority, useWeakReference);
    }

    /**
     * Syntactical sugar for unmapping a listener from an
     * <code>IEventDispatcher</code>
     *
     * @param type
     * @param listener
     * @param eventClass
     * @param useCapture
     */
    protected void removeContextListener(String type, IListener listener,
                                         Class<?> eventClass, Boolean useCapture) {
        this.getEventMap().unmapListener(this.getEventDispatcher(), type,
                listener, eventClass, useCapture);
    }

}
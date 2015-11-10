package com.camnter.robotlegs4android.mvcs;

import com.camnter.robotlegs4android.base.Event;
import com.camnter.robotlegs4android.base.EventMap;
import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.core.IEventDispatcher;
import com.camnter.robotlegs4android.core.IEventMap;

/**
 * Description：Actor
 * Created by：CaMnter
 */
public class Actor {

    /**
     * private
     */
    protected IEventDispatcher _eventDispatcher;

    /**
     * private
     */
    protected IEventMap _eventMap;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    public Actor() {

    }

    // ---------------------------------------------------------------------
    // API
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

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * Local EventMap
     *
     * @return The EventMap for this Actor
     */
    public IEventMap getEventMap() {
        if (this._eventMap == null) {
            this._eventMap = new EventMap(this.getEventDispatcher());
        }
        return this._eventMap;
    }

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
        if (this.getEventDispatcher().hasEventListener(event.getType()))
            return this.getEventDispatcher().dispatchEvent(event);

        return false;
    }

}
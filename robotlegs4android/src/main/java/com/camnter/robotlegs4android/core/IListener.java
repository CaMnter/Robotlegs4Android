package com.camnter.robotlegs4android.core;

import com.camnter.robotlegs4android.base.Event;

/**
 * Description：IListener
 * Created by：CaMnter
 */
public interface IListener {
    /**
     * {@inheritDoc}
     * @return The name of the listener：listener的名称
     */
    public String getName();

    /**
     * {@inheritDoc}
     * @return The type of the listener：listener的类型
     */
    public String getType();

    /**
     * {@inheritDoc}
     * @param type
     *            set type of the listener 设置listener的类型
     */
    public void setType(String type);

    /**
     * {@inheritDoc}
     * @param event
     *            <code>com.camnter.robotlegs4android.base.Event</code>
     */
    public void onHandle(Event event);

}
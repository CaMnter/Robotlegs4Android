package com.camnter.robotlegs4android.core;

/**
 * Description：IContext
 * Created by：CaMnter
 */
public interface IContext {
    /**
     * Get the IEventDispatcher type
     * 取得IEventDispatcher类型
     * {@inheritDoc}
     *
     * @return <code>com.camnter.robotlegs4android.core.IEventDispatcher</code>
     */
    public IEventDispatcher getEventDispatcher();

}
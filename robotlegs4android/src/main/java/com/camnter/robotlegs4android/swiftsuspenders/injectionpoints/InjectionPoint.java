package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectionPoint
 * Created by：CaMnter
 */
public class InjectionPoint {

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param node     node
     * @param injector injector
     */
    public InjectionPoint(XML node, Injector injector) {
        this.initializeInjection(node);
    }

    /**
     * {@inheritDoc}
     * Apply the injection
     * 申请注入器
     *
     * @param target   target
     * @param injector injector
     * @return Object
     */
    public Object applyInjection(Object target, Injector injector) {
        return target;
    }

    /*******************************************************************************************
     * protected methods *
     *******************************************************************************************/
    /**
     * {@inheritDoc}
     * Initialize the injection
     * 实例化注入器
     *
     * @param node node
     */
    protected void initializeInjection(XML node) {
    }

}
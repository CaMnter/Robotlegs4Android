package com.camnter.robotlegs4android.swiftsuspenders.injectionresults;

import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectValueResult
 * Created by：CaMnter
 */
public class InjectValueResult extends InjectionResult {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private final Object m_value;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param value value
     */
    public InjectValueResult(Object value) {
        this.m_value = value;
    }

    /**
     * Get the response
     * 获得响应
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     *
     * @param injector injector
     * @return Object
     */
    @Override
    public Object getResponse(Injector injector) {
        return this.m_value;
    }

}
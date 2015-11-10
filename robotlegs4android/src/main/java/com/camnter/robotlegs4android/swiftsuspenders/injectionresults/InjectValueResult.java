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
    public InjectValueResult(Object value) {
        this.m_value = value;
    }

    /**
     * Get the response
     * <p/>
     * 获得响应
     * <p/>
     * {@inheritDoc}
     * <p/>
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     */
    @Override
    public Object getResponse(Injector injector) {
        return this.m_value;
    }

}
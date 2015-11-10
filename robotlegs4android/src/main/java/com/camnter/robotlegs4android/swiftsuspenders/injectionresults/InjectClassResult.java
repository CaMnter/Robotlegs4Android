package com.camnter.robotlegs4android.swiftsuspenders.injectionresults;

import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectClassResult
 * Created by：CaMnter
 */
public class InjectClassResult extends InjectionResult {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private final Class<?> m_responseType;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    public InjectClassResult(Class<?> responseType) {
        this.m_responseType = responseType;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     */
    @Override
    public Object getResponse(Injector injector) {
        return injector.instantiate(this.m_responseType);
    }

}
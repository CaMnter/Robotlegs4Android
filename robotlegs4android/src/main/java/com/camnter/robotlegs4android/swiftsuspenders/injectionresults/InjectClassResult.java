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
    /**
     * @param responseType responseType
     */
    public InjectClassResult(Class<?> responseType) {
        this.m_responseType = responseType;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     *
     * @param injector injector
     * @return Object
     */
    @Override
    public Object getResponse(Injector injector) {
        return injector.instantiate(this.m_responseType);
    }

}
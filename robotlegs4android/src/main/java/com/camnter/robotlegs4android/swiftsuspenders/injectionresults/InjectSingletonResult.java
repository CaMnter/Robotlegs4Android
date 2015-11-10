package com.camnter.robotlegs4android.swiftsuspenders.injectionresults;

import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectSingletonResult
 * Created by：CaMnter
 */
public class InjectSingletonResult extends InjectionResult {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private final Class<?> m_responseType;
    private Object m_response;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    public InjectSingletonResult(Class<?> responseType) {
        this.m_responseType = responseType;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectionResult #getResponse}
     */
    @Override
    public Object getResponse(Injector injector) {
        if (this.m_response == null) {
            this.m_response = this.createResponse(injector);
        }
        return this.m_response;
    }

    /*******************************************************************************************
     * private methods *
     *******************************************************************************************/
    /**
     * Create the Response
     * 创建响应
     *
     * @param injector
     * @return
     */
    private Object createResponse(Injector injector) {
        return injector.instantiate(this.m_responseType);
    }

}
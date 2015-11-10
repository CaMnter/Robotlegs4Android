package com.camnter.robotlegs4android.swiftsuspenders.injectionresults;

import com.camnter.robotlegs4android.swiftsuspenders.InjectionConfig;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：InjectOtherRuleResult
 * Created by：CaMnter
 */
public class InjectOtherRuleResult extends InjectionResult {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private final InjectionConfig m_rule;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param rule rule
     */
    public InjectOtherRuleResult(InjectionConfig rule) {
        this.m_rule = rule;
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
        return this.m_rule.getResponse(injector);
    }

}
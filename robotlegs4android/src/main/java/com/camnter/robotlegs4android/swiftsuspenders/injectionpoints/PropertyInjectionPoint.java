package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.swiftsuspenders.InjectionConfig;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;
import com.camnter.robotlegs4android.swiftsuspenders.InjectorError;

/**
 * Description：PropertyInjectionPoint
 * Created by：CaMnter
 */
public class PropertyInjectionPoint extends InjectionPoint {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private String _propertyName;
    private String _propertyType;
    private String _injectionName;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param node     node
     * @param injector injector
     */
    public PropertyInjectionPoint(XML node, Injector injector) {
        super(node, null);
    }

    /**
     * {@inheritDoc}
     * Apply the injection
     * 申请注入器
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.InjectionPoint #applyInjection}
     */
    @Override
    public Object applyInjection(Object target, Injector injector) {
        InjectionConfig injectionConfig;
        try {
            injectionConfig = injector.getMapping(
                    Class.forName(this._propertyType), this._injectionName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return target;
        }
        Object injection = injectionConfig.getResponse(injector);
        if (injection == null) {
            throw (new InjectorError(
                    "Injector is missing a rule to handle injection into property \""
                            + this._propertyName + "\" of object \"" + target
                            + "\". Target dependency: \"" + this._propertyType
                            + "\", named \"" + this._injectionName + "\""));
        }
        try {
            target.getClass().getField(this._propertyName)
                    .set(target, injection);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return target;
    }

    /*******************************************************************************************
     * protected methods *
     *******************************************************************************************/
    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.InjectionPoint #initializeInjection}
     * Initialize the injection
     * 初始化注入器
     */
    @Override
    protected void initializeInjection(XML node) {
        this._propertyType = node.parent.getValue("type");
        this._propertyName = node.parent.getValue("name");
        this._injectionName = node.getXMLByName("arg").getValue("value");
    }

}

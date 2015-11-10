package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.base.XMLList;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Description：PostConstructInjectionPoint
 * Created by：CaMnter
 */
public class PostConstructInjectionPoint extends InjectionPoint {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    protected String methodName;

    protected int orderValue;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     * @param node     node
     * @param injector injector
     */
    public PostConstructInjectionPoint(XML node, Injector injector) {
        super(node, injector);
    }

    /**
     * Get the PostConstructInjectionPoint.this.orderValue
     * 获得PostConstructInjectionPoint.this.orderValue
     *
     * @return int
     */
    public int getOrder() {
        return this.orderValue;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.InjectionPoint #applyInjection}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object applyInjection(Object target, Injector injector) {
        try {
            ((Method) ((Map<String, Object>) target).get(this.methodName))
                    .invoke(target);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
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
     */
    @Override
    protected void initializeInjection(XML node) {
        XMLList orderArg = node.getXMLListByName("arg").getXMLListByKeyValue(
                "key", "order");
        XML methodNode = node.parent;
        try {
            this.orderValue = Integer.parseInt(orderArg.get(0)
                    .getValue("value"));
        } catch (Exception e) {
            this.orderValue = 0;
        }
        this.methodName = methodNode.getValue("name");
    }

}
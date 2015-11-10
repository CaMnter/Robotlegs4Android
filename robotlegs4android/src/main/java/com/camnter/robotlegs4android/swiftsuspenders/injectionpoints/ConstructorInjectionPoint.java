package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.base.XMLList;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

import java.util.List;

/**
 * Description：ConstructorInjectionPoint
 * Created by：CaMnter
 */
public class ConstructorInjectionPoint extends MethodInjectionPoint {

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    public ConstructorInjectionPoint(XML node, Class<?> clazz, Injector injector) {
        super(node, injector);
    }

    /**
     * {@inheritDoc}
     * Apply the injection
     * 申请注入器
     * <p/>
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.MethodInjectionPoint #applyInjection}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object applyInjection(Object target, Injector injector) {
        Class<?> ctor = (Class<?>) target;
        Object[] parameters = this.gatherParameterValues(target, injector);
        /*
         * the only way to implement ctor injections, really!
         * 唯一的方法来实现注射,真的!
         */
        switch (((List<?>) parameters[1]).size()) {
            case 0:
                try {
                    return (ctor.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            default:
                try {
                    List<Class<?>> typeList = (List<Class<?>>) (parameters[0]);
                    Class<?>[] typeClasses = new Class<?>[typeList.size()];
                    typeList.toArray(typeClasses);
                    return (ctor.getConstructor(typeClasses));
                } catch (IllegalArgumentException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    /*******************************************************************************************
     * protected methods *
     *******************************************************************************************/

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.MethodInjectionPoint #initializeInjection}
     * Initialize the injection
     * 初始化注入器
     */
    @Override
    protected void initializeInjection(XML node) {
        XMLList nameArgs = node.parent.getXMLListByNameAndKeyValue("metadata",
                "name", Inject.class.getName()).getXMLListByNameAndKeyValue(
                "arg", "key", "name");
        this.methodName = "constructor";
        this.gatherParameters(node, nameArgs);
    }

}
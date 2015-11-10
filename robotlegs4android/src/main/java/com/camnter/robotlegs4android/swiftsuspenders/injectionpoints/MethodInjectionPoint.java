package com.camnter.robotlegs4android.swiftsuspenders.injectionpoints;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.swiftsuspenders.InjectionConfig;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;
import com.camnter.robotlegs4android.swiftsuspenders.InjectorError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Description：MethodInjectionPoint
 * Created by：CaMnter
 */
public class MethodInjectionPoint extends InjectionPoint {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    protected String methodName;

    protected List<Object> _parameterInjectionConfigs;

    protected int requiredParameters = 0;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/

    public MethodInjectionPoint(XML node, Injector injector) {

        super(node, injector);

    }

    /**
     * {@inheritDoc}
     * Apply the injection
     * 申请注入器
     * <p/>
     * {@linkplain com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.InjectionPoint #applyInjection}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object applyInjection(Object target, Injector injector) {
        Object[] parameters = this.gatherParameterValues(target, injector);
        Method method;
        try {
            List<Class<?>> typeList = (List<Class<?>>) (parameters[0]);
            Class<?>[] typeClasses = new Class<?>[typeList.size()];
            typeList.toArray(typeClasses);
            method = target.getClass().getMethod(this.methodName, typeClasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return target;
        }
        try {
            method.invoke(target, ((List<Object>) parameters[1]).toArray());
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
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
        List<XML> nameArgs = new ArrayList<XML>();
        if (node.getXMLByName("arg") != null) {
            nameArgs = node.getXMLByName("arg").getXMLListByKeyValue("key",
                    "name");
        }
        XML methodNode = node.parent;
        this.methodName = methodNode.getValue("name");
        this.gatherParameters(methodNode, nameArgs);
    }

    /**
     * Gather the parameters
     * 收集参数
     *
     * @param methodNode
     * @param nameArgs
     */
    protected void gatherParameters(XML methodNode, List<XML> nameArgs) {
        this._parameterInjectionConfigs = new ArrayList<Object>();
        int i = 0;
        List<XML> parameters = methodNode.getXMLListByName("parameter");
        for (int j = 0; i < parameters.size(); j++) {
            XML parameter = parameters.get(j);
            String injectionName = "";
            if ((nameArgs.size() > 0) && (nameArgs.get(i) != null)) {
                injectionName = nameArgs.get(i).getValue("value");
            }
            String parameterTypeName = parameter.getValue("type");
            if (parameterTypeName.equals("*")) {
                if (parameter.getValue("optional").equals("false")) {
                    throw new InjectorError(
                            "Error in method definition of injectee. "
                                    + "Required parameters can\'t have type \""
                                    + Object.class.getName() + "\".");
                } else {
                    parameterTypeName = null;
                }
            }
            this._parameterInjectionConfigs.add(new ParameterInjectionConfig(
                    parameterTypeName, injectionName));
            if (parameter.getValue("optional").equals("false")) {
                this.requiredParameters++;
            }
            i++;
        }
    }

    /**
     * Gather the value of the parameter
     * <p/>
     * 收集参数的值
     *
     * @param target
     * @param injector
     * @return
     */
    protected Object[] gatherParameterValues(Object target, Injector injector) {
        List<Object> parameters = new ArrayList<Object>();
        List<Class<?>> types = new ArrayList<Class<?>>();
        int length = this._parameterInjectionConfigs.size();
        for (int i = 0; i < length; i++) {
            ParameterInjectionConfig parameterConfig = (ParameterInjectionConfig) this._parameterInjectionConfigs
                    .get(i);
            InjectionConfig config;
            try {
                config = injector.getMapping(
                        Class.forName(parameterConfig.typeName),
                        parameterConfig.injectionName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Object[] result = {types, parameters};
                return result;
            }
            Object injection = config.getResponse(injector);
            if (injection == null) {
                if (i >= this.requiredParameters) {
                    break;
                }

                throw (new InjectorError(
                        "Injector is missing a rule to handle injection into target "
                                + target + ". Target dependency: "
                                + config.request.getName() + ", method: "
                                + this.methodName + ", parameter: " + (i + 1)));
            }
            try {
                types.add(Class.forName(parameterConfig.typeName));
                parameters.add(injection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        Object[] result = {types, parameters};
        return result;
    }

    /**
     * Only be gatherParameters and gatherParameterValues use
     * 仅仅被gatherParameters和gatherParameterValues使用
     *
     * @author CaMnter
     */
    private final class ParameterInjectionConfig {
        public String typeName;
        public String injectionName;

        public ParameterInjectionConfig(String typeName, String injectionName) {
            this.typeName = typeName;
            this.injectionName = injectionName;
        }
    }

}
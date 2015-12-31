/*
 * Copyright (C) 2015 CaMnter 421482590@qq.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camnter.robotlegs4android.swiftsuspenders;

import com.camnter.robotlegs4android.base.Base;
import com.camnter.robotlegs4android.base.Inject;
import com.camnter.robotlegs4android.base.PostConstruct;
import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.base.XMLList;
import com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.ConstructorInjectionPoint;
import com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.InjectionPoint;
import com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.MethodInjectionPoint;
import com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.PostConstructInjectionPoint;
import com.camnter.robotlegs4android.swiftsuspenders.injectionpoints.PropertyInjectionPoint;
import com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectClassResult;
import com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectOtherRuleResult;
import com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectSingletonResult;
import com.camnter.robotlegs4android.swiftsuspenders.injectionresults.InjectValueResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Description：Injector
 * Created by：CaMnter
 */
public class Injector {

    /*******************************************************************************************
     * private properties *
     *******************************************************************************************/
    private static Map<String, Object> INJECTION_POINTS_CACHE = new WeakHashMap<String, Object>();

    private Injector m_parentInjector;

    private final Map<String, Object> m_mappings;

    private Map<String, Object> m_injecteeDescriptions;

    private Map<String, Object> m_attendedToInjectees;

    private final XML m_xmlMetadata;

    /*******************************************************************************************
     * public methods *
     *******************************************************************************************/
    /**
     *
     * @param xmlConfig xmlConfig
     */
    public Injector(XML xmlConfig) {
        this.m_mappings = new HashMap<String, Object>();
        if (xmlConfig != null) {
            this.m_injecteeDescriptions = new WeakHashMap<String, Object>();
        } else {
            this.m_injecteeDescriptions = Injector.INJECTION_POINTS_CACHE;
        }
        this.m_attendedToInjectees = new WeakHashMap<String, Object>();
        this.m_xmlMetadata = xmlConfig;
    }

    /**
     * Set the inject value result
     * 设置注入值的结果
     *
     * @param whenAskedFor whenAskedFor
     * @param useValue     useValue
     * @param named        named
     * @return Object
     */
    public Object mapValue(Class<?> whenAskedFor, Object useValue, String named) {
        InjectionConfig config = this.getMapping(whenAskedFor, named);
        config.setResult(new InjectValueResult(useValue));
        return config;
    }

    /**
     * Set the inject class result
     * 设置注入类的结果
     *
     * @param whenAskedFor     whenAskedFor
     * @param instantiateClass instantiateClass
     * @param named            named
     * @return Object
     */
    public Object mapClass(Class<?> whenAskedFor, Class<?> instantiateClass,
                           String named) {
        InjectionConfig config = this.getMapping(whenAskedFor, named);
        config.setResult(new InjectClassResult(instantiateClass));
        return config;
    }

    /**
     * Invoke the Injector.this.mapSingletonOf method
     * 调用Injector.this.mapSingletonOf方法
     *
     * @param whenAskedFor whenAskedFor
     * @param named        named
     * @return Object
     */
    public Object mapSingleton(Class<?> whenAskedFor, String named) {
        return this.mapSingletonOf(whenAskedFor, whenAskedFor, named);
    }

    /**
     * Set the inject singleton result
     * 设置注入单例的结果
     *
     * @param whenAskedFor   whenAskedFor
     * @param useSingletonOf useSingletonOf
     * @param named          named
     * @return Object
     */
    public Object mapSingletonOf(Class<?> whenAskedFor,
                                 Class<?> useSingletonOf, String named) {
        InjectionConfig config = this.getMapping(whenAskedFor, named);
        config.setResult(new InjectSingletonResult(useSingletonOf));
        return config;
    }

    /**
     * Set the inject other rule result
     * 设置注入其他规则的结果
     *
     * @param whenAskedFor whenAskedFor
     * @param useRule      useRule
     * @param named        named
     * @return Object
     */
    public Object mapRule(Class<?> whenAskedFor, Object useRule, String named) {
        InjectionConfig config = this.getMapping(whenAskedFor, named);
        config.setResult(new InjectOtherRuleResult((InjectionConfig) useRule));
        return useRule;
    }

    /**
     * Get the Mapping(InjectionConfig)
     * 取得映射(InjectionConfig)
     *
     * @param whenAskedFor whenAskedFor
     * @param named        named
     * @return InjectionConfig
     */
    public InjectionConfig getMapping(Class<?> whenAskedFor, String named) {
        String requestName = whenAskedFor.getName();
        InjectionConfig config = (InjectionConfig) this.m_mappings
                .get(requestName + "#" + named);
        if (config == null) {
            InjectionConfig newConfig = new InjectionConfig(whenAskedFor, named);
            this.m_mappings.put(requestName + "#" + named, newConfig);
            config = newConfig;
        }
        return config;
    }

    /**
     * Realize the injection
     * 实现注入
     *
     * @param target target
     */
    public void injectInto(Object target) {
        if ((this.m_attendedToInjectees.get(target.hashCode() + "") != null)
                && (Boolean) this.m_attendedToInjectees.get(target.hashCode()
                + "")) {
            return;
        }

        this.m_attendedToInjectees.put(target.hashCode() + "", true);

        /*
         * get injection points or cache them if this target's class wasn't
         * encountered before
         *
         * 如果这个目标的类不是遇到了之前,得到注入点或缓存它们
         */
        Class<?> targetClass = target.getClass();
        InjecteeDescription injecteeDescription = (this.m_injecteeDescriptions
                .get(targetClass.getName()) != null ? (InjecteeDescription) this.m_injecteeDescriptions
                .get(targetClass.getName()) : this
                .getInjectionPoints(targetClass));
        List<Object> injectionPoints = injecteeDescription.injectionPoints;
        int length = injectionPoints.size();
        for (int i = 0; i < length; i++) {
            InjectionPoint injectionPoint = (InjectionPoint) injectionPoints
                    .get(i);
            injectionPoint.applyInjection(target, this);
        }
    }

    /**
     * Instantiate
     * 实例化
     *
     * @param clazz clazz
     * @return Object
     */
    public Object instantiate(Class<?> clazz) {
        InjecteeDescription injecteeDescription = (InjecteeDescription) this.m_injecteeDescriptions
                .get(clazz.getName());
        if (injecteeDescription == null) {
            injecteeDescription = this.getInjectionPoints(clazz);
        }
        InjectionPoint injectionPoint = injecteeDescription.ctor;
        Object instance = injectionPoint.applyInjection(clazz, this);
        this.injectInto(instance);
        return instance;
    }

    /**
     * Cancel the mapping
     * 取消映射
     *
     * @param clazz clazz
     * @param named named
     */
    public void unmap(Class<?> clazz, String named) {
        InjectionConfig mapping = this.getConfigurationForRequest(clazz, named,
                true);
        if (mapping == null)
            throw new InjectorError(
                    "Error while removing an injector mapping: "
                            + "No mapping defined for class " + clazz.getName()
                            + ", named \"" + named + "\"");
        mapping.setResult(null);
    }

    /**
     * Determine whether there was a mapping
     * 判断是否有映射
     *
     * @param clazz clazz
     * @param named named
     * @return Boolean
     */
    public Boolean hasMapping(Class<?> clazz, String named) {
        InjectionConfig mapping = this.getConfigurationForRequest(clazz, named,
                true);
        if (mapping == null)
            return false;

        return mapping.hasResponse(this);
    }

    /**
     * Get the instance
     * 获得实例
     *
     * @param clazz clazz
     * @param named named
     * @return Object
     */
    public Object getInstance(Class<?> clazz, String named) {
        InjectionConfig mapping = this.getConfigurationForRequest(clazz, named,
                true);
        if ((mapping == null) || !mapping.hasResponse(this)) {
            throw new InjectorError("Error while getting mapping response: "
                    + "No mapping defined for class " + clazz.getName()
                    + ", named \"" + named + "\"");
        }
        return mapping.getResponse(this);

    }

    /**
     * Create the child injector
     * 创建子注入器
     *
     * @return Injector
     */
    public Injector createChildInjector() {
        Injector injector = new Injector(null);
        injector.setParentInjector(this);
        return injector;
    }

    /**
     * Set the parent injector
     * 设置父注入器
     *
     * @param parentInjector parentInjector
     */
    public void setParentInjector(Injector parentInjector) {

        /*
         * restore own map of worked injectees if parent injector is removed
         * 如果删除父注入器时候,恢复自己的工作时候解析映射
         */
        if ((this.m_parentInjector != null) && (parentInjector == null)) {
            this.m_attendedToInjectees = new WeakHashMap<String, Object>();
        }

        this.m_parentInjector = parentInjector;

        /*
         * use parent's map of worked injectees
         * 使用父类的映射的时候解析工作
         */
        if (parentInjector != null) {
            this.m_attendedToInjectees = parentInjector
                    .getAttendedToInjectees();
        }

    }

    /**
     * Get the parent injector
     * 获得父注入器
     *
     * @return Injector
     */
    public Injector getParentInjector() {
        return this.m_parentInjector;
    }

    /**
     * Purge injection points cache
     * 清理注入点缓存
     */
    public void purgeInjectionPointsCache() {
        Injector.INJECTION_POINTS_CACHE = new HashMap<String, Object>();
    }

    /*******************************************************************************************
     * internal methods *
     *******************************************************************************************/

    /**
     * Get the ancestor mapping
     * 获得祖先的映射
     *
     * @param whenAskedFor whenAskedFor
     * @param named        named
     * @return InjectionConfig
     */
    InjectionConfig getAncestorMapping(Class<?> whenAskedFor, String named) {
        Injector parent = this.m_parentInjector;
        while (parent != null) {
            InjectionConfig parentConfig = parent.getConfigurationForRequest(
                    whenAskedFor, named, false);
            if ((parentConfig != null) && parentConfig.hasOwnResponse())
                return parentConfig;
            parent = parent.getParentInjector();
        }
        return null;
    }

    /**
     * Get the Injector.this.m_attendedToInjectees
     * 获得Injector.this.m_attendedToInjectees
     *
     * @return Map
     */
    Map<String, Object> getAttendedToInjectees() {
        return this.m_attendedToInjectees;
    }

    /*******************************************************************************************
     * private methods *
     *******************************************************************************************/

    /**
     * Get the all injection points
     * 获得所有注入点
     *
     * @param clazz clazz
     * @return InjecteeDescription
     */
    private InjecteeDescription getInjectionPoints(Class<?> clazz) {
        XML description = Base.describeType(clazz);
        if ((description.name != "Object")
                && ((description.getXMLByName("factory") == null) || (description
                .getXMLByName("factory").getXMLByName("extendsClass") == null))) {
            throw new InjectorError(
                    "Interfaces can't be used as instantiatable classes.");
        }
        List<Object> injectionPoints = new ArrayList<Object>();
        XML node;
        /*
         * This is where we have to wire in the XML...
         * 这就是我们必须线在XML...
         */
        if (this.m_xmlMetadata != null) {
            this.createInjectionPointsFromConfigXML(description);
            this.addParentInjectionPoints(description, injectionPoints);
        }

        /*
         * get constructor injections
         * 获得构造器注入器
         */
        InjectionPoint ctorInjectionPoint;
        node = description.getXMLByName("factory").getXMLByName("constructor");
        ctorInjectionPoint = new ConstructorInjectionPoint(node, clazz, this);

        /*
         * get injection points for variables
         * 获得变量注入器
         */
        InjectionPoint injectionPoint;
        XMLList childList = (XMLList) description.getXMLByName("factory").child;
        XMLList injectXmlList = childList.findXMLListByName("variable")
                .getXMLListByName("metadata")
                .findXMLListByKeyValue("name", Inject.class.getName());
        for (XML nodeXml : injectXmlList) {
            injectionPoint = new PropertyInjectionPoint(nodeXml, null);
            injectionPoints.add(injectionPoint);
        }

        /*
         * get injection points for methods
         * 获得方法注入器
         */
        for (XML nodeXml : childList.findXMLListByName("method")
                .getXMLListByName("metadata")
                .findXMLListByKeyValue("name", Inject.class.getName())) {
            injectionPoint = new MethodInjectionPoint(nodeXml, this);
            injectionPoints.add(injectionPoint);
        }

        /*
         * get post construct methods
         * 获得构造方法注入器
         */
        List<Object> postConstructMethodPoints = new ArrayList<Object>();
        for (XML nodeXml : childList.findXMLListByName("method")
                .getXMLListByName("metadata")
                .findXMLListByKeyValue("name", "PostConstruct")) {
            injectionPoint = new PostConstructInjectionPoint(nodeXml, this);
            postConstructMethodPoints.add(injectionPoint);
        }

        if (postConstructMethodPoints.size() > 0) {
            Collections.sort(postConstructMethodPoints,
                    new Comparator<Object>() {
                        @Override
                        public int compare(Object o1, Object o2) {
                            int order0 = ((PostConstructInjectionPoint) o1)
                                    .getOrder();
                            int order1 = ((PostConstructInjectionPoint) o2)
                                    .getOrder();
                            return order0 > order1 ? 1 : (order0 == order1 ? 0
                                    : -1);
                        }
                    });
            injectionPoints.addAll(postConstructMethodPoints);
        }

        InjecteeDescription injecteeDescription = new InjecteeDescription(
                ctorInjectionPoint, injectionPoints);
        this.m_injecteeDescriptions.put(clazz.getName(), injecteeDescription);
        return injecteeDescription;

    }

    /**
     * Get injection configuration for request
     * 获得注入配置请求
     *
     * @param clazz             clazz
     * @param named             named
     * @param traverseAncestors traverseAncestors
     * @return InjectionConfig
     */
    private InjectionConfig getConfigurationForRequest(Class<?> clazz,
                                                       String named, Boolean traverseAncestors) {
        String requestName = clazz.getName();
        InjectionConfig config = (InjectionConfig) this.m_mappings
                .get(requestName + "#" + named);
        if ((config == null) && traverseAncestors
                && (this.m_parentInjector != null)
                && this.m_parentInjector.hasMapping(clazz, named)) {
            config = this.getAncestorMapping(clazz, named);
        }
        return config;
    }

    /**
     * Create injection points from configuration XML
     * 从配置XML创建注入点
     *
     * @param description description
     */
    private void createInjectionPointsFromConfigXML(XML description) {

        /*
         * first, clear out all "Inject" meta data, we want a clean slate to
         * have the result work the same in the Flash IDE and MXMLC
         * 首先,清除所有"注入"元数据,我们需要一个干净的石板,结果在Flash IDE和MXMLC相同的工作
         */
        XMLList metadata = description.children().getXMLListByName("metadata");
        XMLList nodes = metadata.getXMLListByKeyValue("name",
                Inject.class.getName());
        nodes.addAll(metadata.getXMLListByKeyValue("name",
                PostConstruct.class.getName()));
        for (XML node : nodes) {
            XML pNode = node.parent;
            pNode.children().removeAll(
                    pNode.getXMLListByNameAndKeyValue("metadata", "name",
                            Inject.class.getName()));
            pNode.children().removeAll(
                    pNode.getXMLListByNameAndKeyValue("metadata", "name",
                            PostConstruct.class.getName()));
        }

        /*
         * now, we create the new injection points based on the given XML file
         * 现在,我们创建新的注入点基于给定的XML文件
         */
        String className = description.getXMLByName("factory").getValue("type");
        for (XML node : this.m_xmlMetadata.getXMLListByNameAndKeyValue("type",
                "name", className).children()) {
            XML metaNode = new XML();
            metaNode.name = "metadata";
            if (node.name.equals(PostConstruct.class.getName())) {
                metaNode.setValue("name", PostConstruct.class.getName());
                if (!node.getValue("order").equals("")) {
                    XML argXml = new XML();
                    argXml.name = "arg";
                    argXml.setValue("key", "order");
                    argXml.setValue("value", node.getValue("order"));
                    argXml.parent = metaNode;
                    metaNode.children().add(argXml);
                }
            } else {
                metaNode.setValue("name", Inject.class.getName());
                if (!node.getValue("injectionname").equals("")) {
                    XML argXml = new XML();
                    argXml.name = "arg";
                    argXml.setValue("key", "name");
                    argXml.setValue("value", node.getValue("injectionname"));
                    argXml.parent = metaNode;
                    metaNode.children().add(argXml);
                }
                for (XML arg : node.getXMLListByName("arg")) {
                    XML argXml = new XML();
                    argXml.name = "arg";
                    argXml.setValue("key", "name");
                    argXml.setValue("value", arg.getValue("injectionname"));
                    argXml.parent = metaNode;
                    metaNode.children().add(argXml);
                }
            }
            XML typeNode = null;
            if (node.name.equals("constructor")) {
                typeNode = description.getXMLByName("factory");
            } else {
                XMLList allChildren = description.getXMLByName("factory")
                        .getAllChildren();
                for (int i = 0; i < allChildren.size(); i++) {
                    if (allChildren.get(i).getValue("name")
                            .equals(node.getValue("name"))) {
                        typeNode = allChildren.get(i);
                        break;
                    }
                }
                if (typeNode == null) {
                    throw new InjectorError(
                            "Error in XML configuration: Class \""
                                    + className
                                    + "\" doesn\'t contain the instance member \""
                                    + node.getValue("name") + "\"");
                }
            }
            typeNode.children().add(metaNode);
            metaNode.parent = typeNode;
        }
    }

    /**
     * Add parent injection points
     * 添加父注入点
     *
     * @param description     description
     * @param injectionPoints injectionPoints
     */
    private void addParentInjectionPoints(XML description,
                                          List<Object> injectionPoints) {
        String parentClassName = description.getXMLByName("factory")
                .getXMLByName("extendsClass").getValue("type");
        if ((parentClassName == null) || parentClassName.equals(""))
            return;

        Class<?> parentClass;
        try {
            parentClass = Class.forName(parentClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        InjecteeDescription parentDescription = (this.m_injecteeDescriptions
                .get(parentClass.hashCode() + "") != null ? (InjecteeDescription) this.m_injecteeDescriptions
                .get(parentClass.hashCode() + "") : this
                .getInjectionPoints(parentClass));
        List<Object> parentInjectionPoints = parentDescription.injectionPoints;
        injectionPoints.addAll(parentInjectionPoints);
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * @author CaMnter
     */
    private final class InjecteeDescription {
        public InjectionPoint ctor;
        public List<Object> injectionPoints;

        public InjecteeDescription(InjectionPoint ctor,
                                   List<Object> injectionPoints) {
            this.ctor = ctor;
            this.injectionPoints = injectionPoints;
        }
    }

}
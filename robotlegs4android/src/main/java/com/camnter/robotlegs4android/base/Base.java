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

package com.camnter.robotlegs4android.base;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Description：Base
 * Created by：CaMnter
 */
public class Base {

    private static final String TAG = "Base";

    /**
     * Through Object getter methods, reflection getter implementation after the
     * value
     * 通过Object对象的getter方法，反射取得getter执行后的value
     *
     * @param fieldName The attribute name 属性名称
     * @param o         To be the Object of the operation 要操作的对象
     * @return The target attribute value 目标属性的value
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase(
                    Locale.ENGLISH);
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            Log.e(Base.TAG, e.getMessage());
            try {
                return o.getClass().getField(fieldName).get(o);
            } catch (Exception e2) {
                Log.e(Base.TAG, e2.getMessage());
                return null;
            }
        }
    }

    /**
     * Get the qualified class name
     * 取得限定类名
     *
     * @param value The class object 类对象
     * @return The name of the class 类名
     */
    public static String getQualifiedClassName(Object value) {
        if (Class.class.isInstance(value)) {
            return ((Class<?>) value).getName();
        } else {
            return value.getClass().getName();
        }
    }

    /**
     * Get all the superclass object
     * 取得所有父类对象
     *
     * @param clazz The class object 类对象
     * @return A collection of all the superclass object 所有父类对象的集合
     */
    public static List<Class<?>> getSuperClasses(Class<?> clazz) {
        if (clazz.getSuperclass() == null) {
            return new ArrayList<>();
        } else {
            Class<?> superClass = clazz.getSuperclass();
            List<Class<?>> classes = Base.getSuperClasses(superClass);
            classes.add(superClass);
            return classes;
        }
    }

    /**
     * Get a collection of all the implementation of the interface
     * 取得所有实现的接口的集合
     *
     * @param clazz The class object 类对象
     * @return A collection of all the implementation of the interface
     * 所有实现的接口的集合
     */
    public static List<Class<?>> getImplementsInterfaces(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<Class<?>>();
        List<Class<?>> superClasses = Base.getSuperClasses(clazz);
        Class<?>[] implementsInterfaces = clazz.getInterfaces();
        result.addAll(Arrays.asList(implementsInterfaces));
        for (int i = 0; i < superClasses.size(); i++) {
            implementsInterfaces = superClasses.get(i).getInterfaces();
            result.addAll(Arrays.asList(implementsInterfaces));
        }
        return result;
    }

    /**
     * *************************************************************************
     * About a class of XML description file (including the parent class,
     * interface, and public mathematics, common methods, the description of the
     * constructor)
     * 取得关于一个类的XML描述文件(包括父类、接口、公共数学、公共方法、构造函数的描述)
     *
     * @param clazz A class object 类对象
     * @return A class of XML description file 类的XML描述文件
     * *****************************************************************
     */
    public static XML describeType(Class<?> clazz) {
        XML xml = new XML();
        xml.name = "type";
        xml.prototype.put("name", clazz.getName());
        xml.child = new XMLList();
        /********************************* 基类 ********************************/
        List<Class<?>> superClasses = Base.getSuperClasses(clazz);
        /********************************* 接口 ********************************/
        List<Class<?>> implementsInterfaces = Base
                .getImplementsInterfaces(clazz);
        /********************************* 属性 ********************************/
        Field[] fields = clazz.getFields();
        /********************************* 方法 ********************************/
        Method[] methods = clazz.getMethods();
        /********************************* 构造 ********************************/
        Constructor<?>[] constructors = clazz.getConstructors();

        /*
         * One by one to get superClasses
         * Then the child nodes of the associated to the main XML
         * 逐个取出superClasses
         * 然后关联到主XML的子节点
         */
        for (int i = 0; i < superClasses.size(); i++) {
            XML extendsClassXml = new XML();
            ((XMLList) xml.child).add(extendsClassXml);
            extendsClassXml.name = "extendsClass";
            extendsClassXml.prototype
                    .put("type", superClasses.get(i).getName());
            extendsClassXml.parent = xml;
        }

        /*
         * Create a factory XML
         *  And then link to the main XML's parent
         * 创建一个factoryXml
         *
         * 然后关联到主XML的父节点
         */
        XML factoryXml = new XML();
        ((XMLList) xml.child).add(factoryXml);
        factoryXml.name = "factory";
        factoryXml.prototype.put("type", clazz.getName());
        factoryXml.child = new XMLList();
        factoryXml.parent = xml;

        /*
         * One by one to get superClasses
         * Then the child nodes of the associated to the main XML
         * 逐个取出superClasses(父类)
         * 然后关联到factoryXml的子节点
         */
        for (int i = 0; i < superClasses.size(); i++) {

            XML extendsClassXml = new XML();

            ((XMLList) factoryXml.child).add(extendsClassXml);

            extendsClassXml.name = "extendsClass";

            extendsClassXml.prototype
                    .put("type", superClasses.get(i).getName());

            extendsClassXml.parent = factoryXml;

        }

        /*
         * One by one to get implementsInterfaces
         * Then the child nodes of the associated to the main XML
         * 逐个取出implementsInterfaces(实现接口)
         * 然后关联到factoryXml的子节点
         */
        for (int i = 0; i < implementsInterfaces.size(); i++) {

            XML interfaceXml = new XML();

            ((XMLList) factoryXml.child).add(interfaceXml);

            interfaceXml.name = "implementsInterface";

            interfaceXml.prototype.put("type", implementsInterfaces.get(i)
                    .getName());

            interfaceXml.parent = factoryXml;

        }

        /*
         * One by one to get constructors
         * And get the constructor parameters one by one
         * The first constructor parameter associated with the child
         * nodes of the constructors
         * Then the constructors nodes associated with factoryXml child
         * nodes
         *
         * 逐个取出constructors(构造函数)
         * 再逐个取出constructor的参数
         * 先constructor的参数关联到constructors的子节点上
         * 然后constructors节点关联到factoryXml的子节点
         */
        for (Constructor<?> constructor : constructors) {
            XML constructorXml = new XML();
            ((XMLList) factoryXml.child).add(constructorXml);
            constructorXml.name = "constructor";
            constructorXml.parent = factoryXml;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int j = 0; j < parameterTypes.length; j++) {
                XML parameterXml = new XML();
                ((XMLList) constructorXml.child).add(parameterXml);
                parameterXml.name = "parameter";
                parameterXml.prototype.put("index", j + "");
                parameterXml.prototype.put("type", parameterTypes[j].getName());
                parameterXml.parent = constructorXml;
            }
        }

        /*
         * One by one to get fields
         * Then the child nodes of the associated to the main XML
         * 逐个取出fields(公共属性)
         * 然后关联到factoryXml的子节点
         */
        for (Field field : fields) {
            XML fieldXml = new XML();
            ((XMLList) factoryXml.child).add(fieldXml);
            fieldXml.name = "variable";
            fieldXml.prototype.put("name", field.getName());
            fieldXml.prototype.put("type", field.getType().getName());
            fieldXml.parent = factoryXml;
            Annotation[] ans = field.getAnnotations();

            /*
             * One by one to get annotations
             * Then the associated to the fields of child nodes
             * 逐个取出annotations(注解)
             * 然后关联到fields的子节点上
             */
            for (Annotation an : ans) {
                XML metadataXml = new XML();
                ((XMLList) fieldXml.child).add(metadataXml);
                metadataXml.name = "metadata";
                metadataXml.prototype.put("name", an.annotationType()
                        .getName());
                metadataXml.parent = fieldXml;
            }

        }

        /*
         * One by one to get methods
         * Then associated with factoryXml child nodes
         * 逐个取出methods(公共方法)
         * 然后关联到factoryXml的子节点上
         */
        for (Method method : methods) {
            XML methodXml = new XML();
            ((XMLList) factoryXml.child).add(methodXml);
            methodXml.name = "method";
            methodXml.prototype.put("name", method.getName());
            methodXml.prototype.put("declaredBy", method
                    .getDeclaringClass().getName());
            methodXml.prototype.put("returnType", method.getReturnType()
                    .getName());
            methodXml.parent = factoryXml;
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[] ans = method.getAnnotations();

            /*
             * One by one to get annotations
             * Then associated with the methods of child nodes
             * 逐个取出annotations(注解)
             * 然后关联到methods的子节点上
             */
            for (Annotation an : ans) {
                XML metadataXml = new XML();
                methodXml.children().add(metadataXml);
                metadataXml.name = "metadata";
                metadataXml.prototype.put("name", an.annotationType()
                        .getName());
                metadataXml.parent = methodXml;
            }

            /*
             * One by one to get parameterTypes
             * Then associated with the methods of child nodes
             * 逐个取出parameterTypes(参数类型)
             * 然后关联到methods的子节点上
             */
            for (int j = 0; j < parameterTypes.length; j++) {
                XML parameterXml = new XML();
                ((XMLList) methodXml.child).add(parameterXml);
                parameterXml.name = "parameter";
                parameterXml.prototype.put("index", j + "");
                parameterXml.prototype.put("type", parameterTypes[j].getName());
                parameterXml.prototype.put("optional", "false");
                parameterXml.parent = methodXml;
            }
        }
        // 返回主XML
        return xml;
    }

}
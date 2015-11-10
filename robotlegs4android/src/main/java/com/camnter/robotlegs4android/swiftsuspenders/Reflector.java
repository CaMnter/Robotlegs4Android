package com.camnter.robotlegs4android.swiftsuspenders;

import com.camnter.robotlegs4android.base.Base;
import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.base.XMLList;

/**
 * Description：Reflector
 * Created by：CaMnter
 */
public class Reflector {

    public Reflector() {

    }

    /**
     * Judge whether a class inherits a class or implements an interface
     * 判断一个类是否继承了某个类或实现了某个接口
     *
     * @param classOrClassName classOrClassName
     * @param superclass superclass
     * @return Boolean
     */
    public Boolean classExtendsOrImplements(Object classOrClassName, Class<?> superclass) {
        Class<?> actualClass = null;
        if (Class.class.isInstance(classOrClassName)) {
            actualClass = (Class<?>) classOrClassName;
        } else if (String.class.isInstance(classOrClassName)) {
            try {
                actualClass = Class.forName((String) classOrClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new Error("The class name " + classOrClassName
                        + " is not valid because of " + e + "\n"
                        + e.getStackTrace());
            }
        }
        if (actualClass == null) {
            throw new Error(
                    "The parameter classOrClassName must be a valid Class "
                            + "instance or fully qualified class name.");
        }
        if (actualClass == superclass)
            return true;

        XML factoryDescription = Base.describeType(actualClass).getXMLByName(
                "factory");
        XMLList children = factoryDescription.children();
        for (int i = 0; i < children.size(); i++) {
            if ((children.get(i).name.equals("implementsInterface") || children
                    .get(i).name.equals("extendsClass"))
                    && children.get(i).getValue("type")
                    .equals(Base.getQualifiedClassName(superclass))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the class object of target object
     * 获取目标对象的类对象
     *
     * @param value value
     * @return Class
     */
    public Class<?> getClass(Object value) {
        if (Class.class.isInstance(value)) {
            return (Class<?>) value;
        }
        return value.getClass();
    }

    /**
     * Get fully qualified class name
     * 获得完全限定类名
     *
     * @param value value
     * @param replaceColons replaceColons
     * @return String
     */
    public String getFullyQualifiedClassName(Object value, Boolean replaceColons) {
        String fullyQualifiedClassName;
        if (String.class.isInstance(value)) {
            fullyQualifiedClassName = (String) value;

            /*
             * Add colons if missing and desired.
             *
             * 如果丢失,需要添加冒号
             */
            if (!replaceColons && (!fullyQualifiedClassName.contains("::"))) {
                int lastDotIndex = fullyQualifiedClassName.lastIndexOf(".");
                if (lastDotIndex == -1)
                    return fullyQualifiedClassName;

                return fullyQualifiedClassName.substring(0, lastDotIndex)
                        + "::"
                        + fullyQualifiedClassName.substring(lastDotIndex + 1);
            }
        } else {
            fullyQualifiedClassName = Base.getQualifiedClassName(value);
        }
        return replaceColons ? fullyQualifiedClassName.replace("::", ".")
                : fullyQualifiedClassName;
    }

}
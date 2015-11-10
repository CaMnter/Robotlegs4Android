package com.camnter.robotlegs4android.core;

/**
 * Description：IReflector
 * Created by：CaMnter
 */
public interface IReflector {

    /**
     * {@inheritDoc}
     * Does this class or class name implement this superclass or interface?
     * 这类或类名实现这个超类或接口?
     *
     * @param classOrClassName
     * @param superclass
     * @return
     */
    public Boolean classExtendsOrImplements(Object classOrClassName,
                                            Class<?> superclass);

    /**
     * {@inheritDoc}
     * Get the class of this instance
     * 获取类的实例
     *
     * @param value
     * @return
     */
    public Class<?> getClass(Object value);

    /**
     * *****************************************************************
     * {@inheritDoc}
     * Get the Fully Qualified Class Name of this instance, class name, or class
     * 这个实例的完全限定类名,类名,或类
     *
     * @param value         The instance, class name, or class 实例,类名,或类
     * @param replaceColons
     * @return The Fully Qualified Class Name 完全限定类名
     * *****************************************************************
     */
    public String getFullyQualifiedClassName(Object value, Boolean replaceColons);

}
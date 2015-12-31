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
     * @param classOrClassName classOrClassName
     * @param superclass superclass
     * @return Boolean
     */
    public Boolean classExtendsOrImplements(Object classOrClassName,
                                            Class<?> superclass);

    /**
     * {@inheritDoc}
     * Get the class of this instance
     * 获取类的实例
     *
     * @param value value
     * @return Class
     */
    public Class<?> getClass(Object value);

    /**
     * *****************************************************************
     * {@inheritDoc}
     * Get the Fully Qualified Class Name of this instance, class name, or class
     * 这个实例的完全限定类名,类名,或类
     *
     * @param value         The instance, class name, or class 实例,类名,或类
     * @param replaceColons replaceColons
     * @return The Fully Qualified Class Name 完全限定类名
     * *****************************************************************
     */
    public String getFullyQualifiedClassName(Object value, Boolean replaceColons);

}
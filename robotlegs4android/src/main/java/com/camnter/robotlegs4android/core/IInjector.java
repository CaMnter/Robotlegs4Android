/*
 * Copyright (C) 2015 CaMnter yuanyu.camnter@gmail.com
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
 * Description：IInjector
 * Created by：CaMnter
 */
public interface IInjector {

    /**
     * {@inheritDoc}
     * When asked for an instance of the class <code>whenAskedFor</code> inject
     * the instance <code>useValue</code>.
     * 被访问类的实例的 whenAskedFor 注入到这个实例中的useValue
     *
     * @param whenAskedFor A class or interface 一个类或接口
     * @param useValue     An instance 一个接口
     * @param named        An optional name (id) 一个可选的名字(id)
     * @return A reference to the rule for this injection. To be used with
     * <code>mapRule</code> 引用的规则注入。与使用mapRule
     */
    Object mapValue(Class<?> whenAskedFor, Object useValue, String named);

    /**
     * {@inheritDoc}
     * When asked for an instance of the class <code>whenAskedFor</code> inject
     * a new instance of <code>instantiateClass</code>.
     * 被访问类的实例的 whenAskedFor 注入到一个新的实例中的instantiateClass
     *
     * @param whenAskedFor     A class or interface 一个类或接口
     * @param instantiateClass An instance 一个接口
     * @param named            An optional name (id) 一个可选的名字(id)
     * @return A reference to the rule for this injection. To be used with
     * <code>mapRule</code> 引用的规则注入。与使用mapRule
     */
    Object mapClass(Class<?> whenAskedFor, Class<?> instantiateClass,
                    String named);

    /**
     * {@inheritDoc}
     * When asked for an instance of the class <code>whenAskedFor</code> inject
     * an instance of <code>whenAskedFor</code>.
     * 被访问类的实例的 whenAskedFor 注入到一个实例中的whenAskedFor
     *
     * @param whenAskedFor A class or interface 一个类或接口
     * @param named        An optional name (id) 一个可选的名字(id)
     * @return A reference to the rule for this injection. To be used with
     * <code>mapRule</code> 引用的规则注入。与使用mapRule
     */
    Object mapSingleton(Class<?> whenAskedFor, String named);

    /**
     * {@inheritDoc}
     * When asked for an instance of the class <code>whenAskedFor</code> inject
     * an instance of <code>useSingletonOf</code>.
     * 被访问类的实例的 whenAskedFor 注入到一个实例中的useSingletonOf
     *
     * @param whenAskedFor   A class or interface 一个类或接口
     * @param useSingletonOf An instance 一个接口
     * @param named          An optional name (id) 一个可选的名字(id)
     * @return A reference to the rule for this injection. To be used with
     * <code>mapRule</code> 引用的规则注入。与使用mapRule
     */
    Object mapSingletonOf(Class<?> whenAskedFor,
                          Class<?> useSingletonOf, String named);

    /**
     * {@inheritDoc}
     * When asked for an instance of the class <code>whenAskedFor</code> use
     * rule <code>useRule</code> to determine the correct injection.
     * 被访问类的实例的 whenAskedFor使用useRule来确定正确的注射。
     *
     * @param whenAskedFor A class or interface 一个类或接口
     * @param useRule      The rule to use for the injection 用于注入的规则
     * @param named        An optional name (id) 一个可选的名字(id)
     * @return A reference to the rule for this injection. To be used with
     * <code>mapRule</code> 引用的规则注入。与使用mapRule
     */
    Object mapRule(Class<?> whenAskedFor, Object useRule, String named);

    /**
     * {@inheritDoc}
     * Perform an injection into an object, satisfying all it's dependencies
     * 执行一个注入一个对象,满足所有的依赖项
     * The <code>IInjector</code> should throw an <code>Error</code> if it can't
     * satisfy all dependencies of the injectee.
     * 如果不能错误 满足所有依赖项的时候解析,IInjector应该抛出一个Error。
     *
     * @param target The object to inject into - the Injectee 对象注入到——时候解析
     */
    void injectInto(Object target);

    /**
     * {@inheritDoc}
     * Create an object of the given class, supplying its dependencies as
     * constructor parameters if the used DI solution has support for
     * constructor injection
     * 创建一个对象的类,提供其依赖关系。如果使用构造函数参数DI解决方案支持构造函数注入。
     * Adapters for DI solutions that don't support constructor injection should
     * just create a new instance and perform setter and/ or method injection on
     * that.
     * 适配器DI的解决方案不支持构造函数注入只创建一个新的实例并进行setter注入和/或方法
     * NOTE: This method will always create a new instance. If you need to
     * retrieve an instance consider using <code>getInstance</code>
     * 注意:这个方法总是会创建一个新的实例。如果你需要检索实例考虑使用getInstance
     * The <code>IInjector</code> should throw an <code>Error</code> if it can't
     * satisfy all dependencies of the injectee.
     * 如果不能错误满足所有依赖项的时候解析,IInjector应该抛出一个Error。
     *
     * @param clazz The class to instantiate 要实例化的类
     * @return The created instance 创建实例
     */
    Object instantiate(Class<?> clazz);

    /**
     * {@inheritDoc}
     * Create or retrieve an instance of the given class
     * 创建或检索给定类的一个实例
     *
     * @param clazz The class to instantiate 要实例化的类
     * @param named An optional name (id) 一个可选的名字(id)
     * @return An instance 一个实例
     */
    Object getInstance(Class<?> clazz, String named);

    /**
     * {@inheritDoc}
     * Create an injector that inherits rules from its parent
     * 创建一个注射器,从父母继承规则
     *
     * @return The injector 这个注入器
     */
    IInjector createChild();

    /**
     * {@inheritDoc}
     * Remove a rule from the injector
     * 从注射器中删除一个规则
     *
     * @param clazz A class or interface 一个类或接口
     * @param named 一个可选的名字(id)
     */
    void unmap(Class<?> clazz, String named);

    /**
     * {@inheritDoc}
     * Does a rule exist to satsify such a request? 一个规则存在satsify这样的请求吗?
     *
     * @param clazz A class or interface 一个类或接口
     * @param named 一个可选的名字(id)
     * @return Whether such a mapping exists 是否存在这样的一个映射
     */
    Boolean hasMapping(Class<?> clazz, String named);

    /**
     * @return The Application Domain
     */
    // public ApplicationDomain getApplicationDomain();

    /**
     * @param value
     *            The Application Domain
     */
    // public void setApplicationDomain(ApplicationDomain value);

}
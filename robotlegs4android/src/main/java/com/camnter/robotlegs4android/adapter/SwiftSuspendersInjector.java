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


package com.camnter.robotlegs4android.adapter;

import com.camnter.robotlegs4android.base.XML;
import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.mvcs.Actor;
import com.camnter.robotlegs4android.mvcs.Command;
import com.camnter.robotlegs4android.mvcs.Mediator;
import com.camnter.robotlegs4android.swiftsuspenders.Injector;

/**
 * Description：SwiftSuspendersInjector
 * Created by：CaMnter
 */
public class SwiftSuspendersInjector extends Injector implements IInjector {

    protected static final XML XML_CONFIG = SwiftSuspendersInjector
            .initXML_CONFIG();

    /**
     * Init the XML config
     * 初始化XML配置
     *
     * @return XML
     */
    private static XML initXML_CONFIG() {
        XML result = new XML();
        result.setName("types")
                .appendChild(
                        new XML()
                                .setName("type")
                                .setValue("name", Command.class.getName())
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "eventDispatcher")))
                .appendChild(
                        new XML()
                                .setName("type")
                                .setValue("name", Actor.class.getName())
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "contextView"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "mediatorMap"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "eventDispatcher"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "injector"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "commandMap")))
                .appendChild(
                        new XML()
                                .setName("type")
                                .setValue("name", Mediator.class.getName())
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "contextView"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "mediatorMap"))
                                .appendChild(
                                        new XML().setName("field").setValue(
                                                "name", "eventDispatcher")));
        return result;
    }

    /**
     * Get the parameters if constructor
     * 获得构造方法参数
     *
     * @param xmlConfig xmlConfig
     * @return XML
     */
    private static XML getConstructParam(XML xmlConfig) {
        if (xmlConfig != null) {
            for (XML typeNode : SwiftSuspendersInjector.XML_CONFIG.children()) {
                xmlConfig.appendChild(typeNode);
            }
        }
        return xmlConfig;
    }

    /**
     * The constructor
     * 构造方法
     *
     * @param xmlConfig xmlConfig
     */
    public SwiftSuspendersInjector(XML xmlConfig) {
        super(SwiftSuspendersInjector.getConstructParam(xmlConfig));
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IInjector #createChild}
     */
    @Override
    public IInjector createChild() {
        SwiftSuspendersInjector injector = new SwiftSuspendersInjector(null);
        injector.setParentInjector(this);
        return injector;
    }

}
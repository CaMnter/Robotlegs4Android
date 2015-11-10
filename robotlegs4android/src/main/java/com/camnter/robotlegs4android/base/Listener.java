package com.camnter.robotlegs4android.base;

import com.camnter.robotlegs4android.core.IListener;

/**
 * Description：Listener
 * Created by：CaMnter
 */
public class Listener implements IListener {

    private String _type;
    private final String _name;

    /**
     * Need the type and the name arguments
     * 需要参数type和name
     *
     * @param type The listener's type listener的类型
     * @param name The listener's name listener的名称
     */
    public Listener(String type, String name) {
        this._type = type;
        this._name = name;
    }

    /**
     * Need the name argument
     * 需要参数name
     *
     * @param name The listener's name listener的名称
     */
    public Listener(String name) {
        this._name = name;
    }

    /**
     * No arguments constructor
     * 无参构造函数
     */
    public Listener() {
        this._name = this.hashCode() + "";
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IListener #setType}
     */
    @Override
    public void setType(String type) {
        this._type = type;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IListener #getType}
     */
    @Override
    public String getType() {
        return this._type;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IListener #getName}
     */
    @Override
    public String getName() {
        return this._name;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IListener #onHandle}
     */
    @Override
    public void onHandle(Event event) {
    }

}
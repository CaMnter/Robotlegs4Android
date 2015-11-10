package com.camnter.robotlegs4android.core;

/**
 * Description：IViewMap
 * Created by：CaMnter
 */
public interface IViewMap {

    /**
     * {@inheritDoc}
     * Map an entire package (including sub-packages) for automatic injection
     * 映射整个包（包括子包）自动注入
     *
     * @param packageName The substring to compare 子字符串比较
     */
    public void mapPackage(String packageName);

    /**
     * {@inheritDoc}
     * Unmap a package
     * 不映射一个包
     *
     * @param packageName The substring to compare 子字符串比较
     */
    public void unmapPackage(String packageName);

    /**
     * {@inheritDoc}
     * Check if a package has been registered for automatic injection
     * 检查是否一个包已经注册自动注入
     *
     * @param packageName The substring to compare 子字符串比较
     * @return Whether a package has been registered for automatic injection
     * 一个包是否已经注册自动注入
     */
    public Boolean hasPackage(String packageName);

    /**
     * {@inheritDoc}
     * Map a view component class or interface for automatic injection
     * 映射视图组件自动注入的类或接口
     *
     * @param type The concrete view Interface 具体的视图界面
     */
    public void mapType(Class<?> type);

    /**
     * {@inheritDoc}
     * Unmap a view component class or interface
     * 不映射一个视图组件或者接口
     *
     * @param type The concrete view Interface 具体的视图界面
     */
    public void unmapType(Class<?> type);

    /**
     * {@inheritDoc}
     * Check if a class or interface has been registered for automatic injection
     * 检查是否一个类或接口注册自动注入
     *
     * @param type The concrete view Interface 具体的视图界面
     * @return Whether an interface has been registered for automatic injection
     * 一个包是否已经注册自动注入
     */
    public Boolean hasType(Class<?> type);

    /**
     * {@inheritDoc}
     * The <code>IViewMap</code>'s <code>DisplayObjectContainer</code>
     * IViewMap的DisplayObjectContainer
     *
     * @return view The <code>DisplayObjectContainer</code> to use as scope for
     * this <code>IViewMap</code> DisplayObjectContainer使用作为这IViewMap范围
     */
    public Object getContextView();

    /**
     * {@inheritDoc}
     * The <code>IViewMap</code>'s <code>DisplayObjectContainer</code>
     * IViewMap的DisplayObjectContainer
     *
     * @param value The <code>DisplayObjectContainer</code> to use as scope for
     *              this <code>IViewMap</code>
     *              DisplayObjectContainer使用作为这IViewMap范围
     */
    public void setContextView(Object value);

    /**
     * {@inheritDoc}
     * Get the <code>IViewMap</code>'s enabled status
     * 取得IViewMap的启用状态
     *
     * @return Whether the <code>IViewMap</code> is enabled IViewMap是否启用
     */
    public Boolean getEnabled();

    /**
     * {@inheritDoc}
     * Set the <code>IViewMap</code>'s enabled status
     * 设置IViewMap的启用状态
     *
     * @param value the <code>IViewMap</code> is enabled IViewMap是否启用
     */
    public void setEnabled(Boolean value);

}
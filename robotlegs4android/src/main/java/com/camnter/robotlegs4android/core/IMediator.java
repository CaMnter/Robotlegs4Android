package com.camnter.robotlegs4android.core;

/**
 * Description：IMediator
 * Created by：CaMnter
 */
public interface IMediator {

    /**
     * {@inheritDoc}
     * 注册前
     * Should be invoked by the <code>IMediatorMap</code> during
     * <code>IMediator</code> registration
     * 在IMediator注册时,应该被IMediatorMap调用。
     */
    public void preRegister();

    /**
     * {@inheritDoc}
     * 注册时
     * Should be invoked by the <code>IMediator</code> itself when it is ready
     * to be interacted with
     * 在本身当它准备好了与之交互时,应该被IMediator调用。
     * Override and place your initialization code here
     * 覆盖,这里的初始化代码
     */
    public void onRegister();

    /**
     * {@inheritDoc}
     * 删除前
     * Invoked when the <code>IMediator</code> has been removed by the
     * <code>IMediatorMap</code>
     * IMediator 被删除前，被IMediatorMap调用.
     */
    public void preRemove();

    /**
     * {@inheritDoc}
     * 删除时
     * Should be invoked by the <code>IMediator</code> itself when it is ready
     * to for cleanup
     * 在本身当它准备好了清理时,应该被IMediator调用。
     * Override and place your cleanup code here
     * 覆盖,这里的初始化代码
     */
    public void onRemove();

    /**
     * {@inheritDoc}
     * Get the <code>IMediator</code>'s view component
     * 取得IMediator的视图组件
     *
     * @return The view component 视图组件
     */
    public Object getViewComponent();

    /**
     * {@inheritDoc}
     * Set the <code>IMediator</code>'s view component
     * 设置IMediator的视图组件
     *
     * @param viewComponent The view component 视图组件
     */
    public void setViewComponent(Object viewComponent);

}

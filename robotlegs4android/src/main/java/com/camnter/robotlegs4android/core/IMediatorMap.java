package com.camnter.robotlegs4android.core;

/**
 * Description：IMediatorMap
 * Created by：CaMnter
 */
public interface IMediatorMap {

    /**
     * {@inheritDoc}
     * Map an <code>IMediator</code> to a view Class 一个IMediator映射到一个视图类
     *
     * @param viewClassOrName The concrete view Class or Fully Qualified Class Name
     *                        具体的视图类或完全限定类名
     * @param mediatorClass   The <code>IMediator</code> Class IMediator类
     * @param injectViewAs    The explicit view Interface or Class that the mediator depends
     *                        on OR an Array of such Interfaces/Classes.
     *                        显式视图接口或类,中介取决于这样的接口/类或数组。
     * @param autoCreate      Automatically construct and register an instance of Class
     *                        <code>mediatorClass</code> when an instance of Class
     *                        <code>viewClass</code> is detected
     *                        自动构建和注册类的一个实例mediatorClass当检测到viewClass类的一个实例
     * @param autoRemove      Automatically remove an instance of Class
     *                        <code>mediatorClass</code> when its <code>viewClass</code>
     *                        leaves the ancestors of the context view
     *                        viewClass离开的父类上下文视图时,自动删除类的一个实例mediatorClass
     */
    public void mapView(Object viewClassOrName, Class<?> mediatorClass,
                        Object injectViewAs, Boolean autoCreate, Boolean autoRemove);

    /**
     * {@inheritDoc}
     * Unmap a view Class
     * 取消一个视图类
     *
     * @param viewClassOrName The concrete view Class or Fully Qualified Class Name
     *                        具体的视图类或完全限定类名
     */
    public void unmapView(Object viewClassOrName);

    /**
     * {@inheritDoc}
     * Create an instance of a mapped <code>IMediator</code>
     * 创建一个实例的映射 IMediator
     * This will instantiate and register a Mediator for a given View Component.
     * Mediator dependencies will be automatically resolved.
     * 这将实例化并注册一个中介对于一个给定的视图组件。中介的依赖性会自动解决。
     *
     * @param viewComponent An instance of the view Class previously mapped to an
     *                      <code>IMediator</code> Class 视图类的一个实例之前映射到一个IMediator类
     * @return The <code>IMediator</code>
     */
    public IMediator createMediator(Object viewComponent);

    /**
     * {@inheritDoc}
     * Manually register an <code>IMediator</code> instance
     * 手动注册一个IMediator实例
     * NOTE: Registering a Mediator will NOT inject its dependencies. It is
     * assumed that dependencies are already satisfied.
     * 注意:注册一个中介不会注入依赖项。这是 假设依赖已经满意了。
     *
     * @param viewComponent The view component for the <code>IMediator</code>
     *                      IMediator的视图组件
     * @param mediator      The <code>IMediator</code> to register IMediator注册
     */
    public void registerMediator(Object viewComponent, IMediator mediator);

    /**
     * {@inheritDoc}
     * Remove a registered <code>IMediator</code> instance 删除注册IMediator实例
     *
     * @param mediator The <code>IMediator</code> to remove 要删除的IMediator
     * @return The <code>IMediator</code> that was removed 被删除的IMediator
     */
    public IMediator removeMediator(IMediator mediator);

    /**
     * {@inheritDoc}
     * UnInject to the Mediator
     * 不注入Mediator
     *
     * @param viewComponent The view component for the <code>IMediator</code>
     *                      IMediator的视图组件
     */
    public void unInjectMediator(Object viewComponent);

    /**
     * {@inheritDoc}
     * Remove a registered <code>IMediator</code> instance
     * 通过view删除一个注册过的IMediator实例
     *
     * @param viewComponent The view that the <code>IMediator</code> was registered with
     *                      IMediator注册的view组件
     * @return The <code>IMediator</code> that was removed 被删除的IMediator
     */
    public IMediator removeMediatorByView(Object viewComponent);

    /**
     * {@inheritDoc}
     * Retrieve a registered <code>IMediator</code> instance
     * 检索一个注册过的IMediator实例
     *
     * @param viewComponent The view that the <code>IMediator</code> was registered with
     *                      IMediator注册的view组件
     * @return The <code>IMediator</code> 被检索的IMediator
     */
    public IMediator retrieveMediator(Object viewComponent);

    /**
     * {@inheritDoc}
     * Check if the view Class has been mapped or not
     * 检查这个视图类是否映射
     *
     * @param viewClassOrName The concrete view Class or Fully Qualified Class Name
     *                        具体的视图类或完全限定类名
     * @return Whether this view class has been mapped 这一视图类是否已经映射
     */
    public Boolean hasMapping(Object viewClassOrName);

    /**
     * {@inheritDoc}
     * Check if the <code>IMediator</code> has been registered
     * 检查这个IMediator是否已经注册过了
     *
     * @param mediator The <code>IMediator</code> instance IMediator实例
     * @return Whether this <code>IMediator</code> has been registered
     * 这是否IMediator已经注册
     */
    public Boolean hasMediator(IMediator mediator);

    /**
     * {@inheritDoc}
     * Check if an <code>IMediator</code> has been registered for a view
     * instance
     * 检查是否一个IMediator已经注册了一个视图 实例
     *
     * @param viewComponent The view that the <code>IMediator</code> was registered with
     *                      IMediator注册的view组件
     * @return Whether an <code>IMediator</code> has been registered for this
     * view instance IMediator是否已经注册了这个视图实例
     */
    public Boolean hasMediatorForView(Object viewComponent);

    /**
     * {@inheritDoc}
     * The <code>IMediatorMap</code>'s <code>DisplayObjectContainer</code>
     * IMediatorMap的DisplayObjectContainer
     *
     * @return The <code>DisplayObjectContainer</code> to use as scope for this
     * <code>IMediatorMap</code>
     * DisplayObjectContainer使用作为这IMediatorMap范围
     */
    public Object getContextView();

    /**
     * {@inheritDoc}
     * The <code>IMediatorMap</code>'s <code>DisplayObjectContainer</code>
     * IMediatorMap的DisplayObjectContainer
     *
     * @param value The <code>DisplayObjectContainer</code> to use as scope for
     *              this <code>IMediatorMap</code>
     *              DisplayObjectContainer使用作为这IMediatorMap范围
     */
    public void setContextView(Object value);

    /**
     * {@inheritDoc}
     * The <code>IMediatorMap</code>'s enabled status
     * IMediatorMap的启用状态
     *
     * @return Whether the <code>IMediatorMap</code> should be enabled
     * IMediatorMap是否应该启用
     */
    public Boolean getEnabled();

    /**
     * {@inheritDoc}
     * Add an <code>IMediator</code> instance
     * 添加一个IMediator
     *
     * @param viewComponent The view component for the <code>IMediator</code>
     *                      IMediator的视图组件
     */
    public void addMediator(Object viewComponent);

}
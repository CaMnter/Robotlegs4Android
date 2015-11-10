package com.camnter.robotlegs4android.base;

import android.util.Log;

import com.camnter.robotlegs4android.core.IInjector;
import com.camnter.robotlegs4android.core.IMediator;
import com.camnter.robotlegs4android.core.IMediatorMap;
import com.camnter.robotlegs4android.core.IReflector;
import com.camnter.robotlegs4android.expand.IFragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：MediatorMap
 * Created by：CaMnter
 */
public class MediatorMap extends ViewMapBase implements IMediatorMap {

    /**
     * private
     */
    protected Map<String, Object> mediatorByView;

    /**
     * private
     */
    protected Map<String, Object> mappingConfigByView;

    /**
     * private
     */
    protected Map<String, Object> mappingConfigByViewClassName;

    /**
     * private
     */
    protected Map<String, Object> mediatorsMarkedForRemoval;

    /**
     * private
     */
    protected Boolean hasMediatorsMarkedForRemoval;

    /**
     * private
     */
    protected IReflector reflector;

    // ---------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------

    /**
     * Create a new <code>MediatorMap</code> object
     * 创建一个新的MediatorMap对象
     *
     * @param contextView The root view node of the context. The map will listen for
     *                    ADDED_TO_STAGE events on this node
     *                    上下文的根节点视图。map将在这个节点监听ADDED_TO_STAGE事件
     * @param injector    An <code>IInjector</code> to use for this context
     *                    这种情况下的IInjector使用
     * @param reflector   An <code>IReflector</code> to use for this context
     *                    这种情况下的IReflector使用
     */
    public MediatorMap(Object contextView, IInjector injector, IReflector reflector) {
        super(contextView, injector);
        this.reflector = reflector;
        /*
         * mappings - if you can do it with fewer dictionaries you get a prize
         * 映射——如果你可以用更少的字典你得到一个奖
         */
        this.mediatorByView = new HashMap<String, Object>();
        this.mappingConfigByView = new HashMap<String, Object>();
        this.mediatorsMarkedForRemoval = new HashMap<String, Object>();
        this.mappingConfigByViewClassName = new HashMap<String, Object>();
    }

    // ---------------------------------------------------------------------
    // API
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #mapView}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void mapView(Object viewClassOrName, Class<?> mediatorClass, Object injectViewAs, Boolean autoCreate, Boolean autoRemove) {
        String viewClassName = this.reflector.getFullyQualifiedClassName(
                viewClassOrName, false);
        if (this.mappingConfigByViewClassName.get(viewClassName) != null) {
            throw new ContextError(ContextError.E_MEDIATORMAP_OVR + " - "
                    + mediatorClass);
        }

        if (!this.reflector.classExtendsOrImplements(mediatorClass,
                IMediator.class)) {
            throw new ContextError(ContextError.E_MEDIATORMAP_NOIMPL + " - "
                    + mediatorClass);
        }

        MappingConfig config = new MappingConfig();
        config.mediatorClass = mediatorClass;
        config.autoCreate = autoCreate;
        config.autoRemove = autoRemove;

        if (injectViewAs != null) {
            if (List.class.isInstance(injectViewAs)) {
                config.typedViewClasses = (ArrayList<Object>) ((ArrayList<Object>) injectViewAs)
                        .clone();
            } else if (Class.class.isInstance(injectViewAs)) {
                config.typedViewClasses = new ArrayList<Object>(
                        Arrays.asList(injectViewAs));
            }
        } else if (Class.class.isInstance(viewClassOrName)) {
            config.typedViewClasses = new ArrayList<Object>(
                    Arrays.asList(viewClassOrName));
        }

        this.mappingConfigByViewClassName.put(viewClassName, config);

        if (autoCreate || autoRemove) {
            this.viewListenerCount++;
            if (this.viewListenerCount == 1) {
                this.addListeners();
            }
        }

        /*
         * This was a bad idea - causes unexpected eager instantiation of object
         * graph
         * 这是一个坏主意-使意想不到的急切的实例化对象图
         */
        if (autoCreate
                && (this.getContextView() != null)
                && (viewClassName.equals(this.getContextView().getClass()
                .getName()))) {
            this.createMediatorUsing(this.getContextView(), viewClassName,
                    config);
        }

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #unmapView}
     */
    @Override
    public void unmapView(Object viewClassOrName) {
        String viewClassName = this.reflector.getFullyQualifiedClassName(
                viewClassOrName, false);
        MappingConfig config = (MappingConfig) this.mappingConfigByViewClassName
                .get(viewClassName);
        if ((config != null) && (config.autoCreate || config.autoRemove)) {
            this.viewListenerCount--;
            if (this.viewListenerCount == 0) {
                this.removeListeners();
            }
        }
        this.mappingConfigByViewClassName.remove(viewClassName);

    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #createMediator}
     */
    @Override
    public IMediator createMediator(Object viewComponent) {
        return this.createMediatorUsing(viewComponent, "", null);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #registerMediator}
     */
    @Override
    public void registerMediator(Object viewComponent, IMediator mediator) {
        /*
         * Only as IFragmentActivity instance need to delete the back click
         * 只有作为IFragmentActivity的实例需要删除回退点击
         */
        if (viewComponent instanceof IFragmentActivity) {
            ((IFragmentActivity) viewComponent).replaceOnBackClickListener(null);
        }
        Class<?> mediatorClass = this.reflector.getClass(mediator);
        if (this.injector.hasMapping(mediatorClass, "")) {
            this.injector.unmap(mediatorClass, "");
        }
        this.injector.mapValue(mediatorClass, mediator, "");
        this.mediatorByView.put(viewComponent.hashCode() + "", mediator);
        this.mappingConfigByView.put(viewComponent.hashCode() + "",
                this.mappingConfigByViewClassName.get(viewComponent.getClass()
                        .getName()));
        mediator.setViewComponent(viewComponent);
        mediator.preRegister();
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #removeMediator}
     */
    @Override
    public IMediator removeMediator(IMediator mediator) {
        if (mediator != null) {
            Object viewComponent = mediator.getViewComponent();
            Class<?> mediatorClass = this.reflector.getClass(mediator);
            this.mediatorByView.remove(viewComponent.hashCode() + "");
            this.mappingConfigByView.remove(viewComponent.hashCode() + "");
            mediator.preRemove();
            mediator.setViewComponent(null);
            if (this.injector.hasMapping(mediatorClass, "")) {
                this.injector.unmap(mediatorClass, "");
            }
        }
        Log.e("removeMediator_mediator", mediator.hashCode() + "");
        return mediator;
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #removeMediatorByView}
     */
    @Override
    public IMediator removeMediatorByView(Object viewComponent) {
        return this.removeMediator(this.retrieveMediator(viewComponent));
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #retrieveMediator}
     */
    @Override
    public IMediator retrieveMediator(Object viewComponent) {
        return (IMediator) this.mediatorByView.get(viewComponent.hashCode()
                + "");
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #hasMapping}
     */
    @Override
    public Boolean hasMapping(Object viewClassOrName) {
        String viewClassName = this.reflector.getFullyQualifiedClassName(
                viewClassOrName, false);
        return (this.mappingConfigByViewClassName.get(viewClassName) != null);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #hasMediatorForView}
     */
    @Override
    public Boolean hasMediatorForView(Object viewComponent) {
        return (this.mediatorByView.get(viewComponent.hashCode() + "") != null);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #hasMediator}
     */
    @Override
    public Boolean hasMediator(IMediator mediator) {
        for (Object med : this.mediatorByView.values()) {
            if (med.hashCode() == mediator.hashCode())
                return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // Internal
    // ---------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.base.ViewMapBase #addListeners}
     */
    @Override
    protected void addListeners() {
        if ((this.getContextView() != null) && this.getEnabled()) {
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.base.ViewMapBase #removeListeners}
     */
    @Override
    protected void removeListeners() {
        if (this.getContextView() != null) {
        }
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #addMediator}
     */
    @Override
    public void addMediator(Object viewComponent) {
        this.onViewAdded(viewComponent);
    }

    /**
     * {@inheritDoc}
     * {@linkplain com.camnter.robotlegs4android.core.IMediatorMap #unInjectMediator}
     */
    @Override
    public void unInjectMediator(Object viewComponent) {
        this.onViewRemoved(viewComponent);
    }

    /**
     * When the view is added
     * 当view被添加时
     *
     * @param view view
     */
    protected void onViewAdded(Object view) {
        if (this.mediatorsMarkedForRemoval.get(view.hashCode() + "") != null) {
            this.mediatorsMarkedForRemoval.remove(view.hashCode() + "");
            return;
        }
        String viewClassName = view.getClass().getName();
        MappingConfig config = (MappingConfig) this.mappingConfigByViewClassName
                .get(viewClassName);
        if ((config != null) && config.autoCreate) {
            this.createMediatorUsing(view, viewClassName, config);
        }
    }

    /**
     * When the Mediator to create or are using
     * 当Mediator创建或者正在使用时
     *
     * @param viewComponent viewComponent
     * @param viewClassName viewClassName
     * @param config        config
     * @return IMediator
     */
    protected IMediator createMediatorUsing(Object viewComponent, String viewClassName, MappingConfig config) {
        IMediator mediator = (IMediator) this.mediatorByView.get(viewComponent
                .hashCode() + "");
        if (mediator == null) {
            if (viewClassName.equals("")) {
                viewClassName = viewComponent.getClass().getName();
            }
            if (config == null) {
                config = (MappingConfig) this.mappingConfigByViewClassName
                        .get(viewClassName);
            }
            if (config != null) {
                for (Object classObject : config.typedViewClasses) {
                    this.injector.mapValue((Class<?>) classObject,
                            viewComponent, "");
                }
                mediator = (IMediator) this.injector
                        .instantiate(config.mediatorClass);
                for (Object clazz : config.typedViewClasses) {
                    this.injector.unmap((Class<?>) clazz, "");
                }
                this.registerMediator(viewComponent, mediator);
            }
        }
        return mediator;
    }

    /**
     * Flex framework work-around part #5
     * When the view is removed
     * Flex框架的变通办法 #5
     * 当view被删除时
     *
     * @param view view
     */
    protected void onViewRemoved(Object view) {
        MappingConfig config = (MappingConfig) this.mappingConfigByView
                .get(view.hashCode() + "");
        if ((config != null) && config.autoRemove) {
            this.mediatorsMarkedForRemoval.put(view.hashCode() + "", view);
            if ((this.hasMediatorsMarkedForRemoval == null)
                    || !this.hasMediatorsMarkedForRemoval) {
                this.hasMediatorsMarkedForRemoval = true;
                this.removeMediatorLater();
            }
        }
    }

    /**
     * Flex framework work-around part #6
     * When remove the Mediator later
     * Flex框架的变通办法 #6
     * 当删除Mediator之后
     */
    protected void removeMediatorLater() {
        for (Object view : this.mediatorsMarkedForRemoval.values()) {
            // if (!view.stage)
            // removeMediatorByView(view);
            this.removeMediatorByView(view);
            this.mediatorsMarkedForRemoval.remove(view.hashCode() + "");
        }
        this.hasMediatorsMarkedForRemoval = false;
    }

    /**
     * Record mapping configuration information
     * 记录映射配置信息
     *
     * @author CaMnter
     */
    class MappingConfig {
        public Class<?> mediatorClass;
        public List<Object> typedViewClasses;
        public Boolean autoCreate;
        public Boolean autoRemove;
    }

}
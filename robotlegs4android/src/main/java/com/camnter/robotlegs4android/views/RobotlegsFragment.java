package com.camnter.robotlegs4android.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camnter.robotlegs4android.base.EventDispatcher;
import com.camnter.robotlegs4android.expand.IApplication;

/**
 * Description：RobotlegsFragment
 * Created by：CaMnter
 * Time：2015-11-08 00:25
 */
public abstract class RobotlegsFragment extends Fragment {

    public View self;

    /**
     * Please set the fragment layout id
     * 请设置Fragment的布局Id
     *
     * @return int
     */
    public abstract int getLayoutId();

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * onCreate and onActivityCreated(Bundle)
     * If you return a View from here, you will later be called in
     * onDestroyView when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {
            if (this.getLayoutId() != 0) {
                this.self = inflater.inflate(this.getLayoutId(), null);
                /*
                 * inject the fragment's mediator when it on create view
                 * 在 onCreateView 的时候 注入这个Fragment的mediator
                 */
                try {
                    ((IApplication) this.getActivity().getApplication()).getMvcContext().injectMediator(this);
                    EventDispatcher.setDispatcher(this.getClass().getSimpleName() + this.hashCode() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Please create a custom RobotlegsApplication and fill in the getMvcContextInstance() method");
                }
                this.onCreatingView(this.self);
            }
        }
        if ((this.self != null ? this.self.getParent() : null) != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }

    /**
     * When on creating fragment view
     * you can write findViewById() in here
     * 正在创建fragment的时候
     * 你可以在这里写findViewById()
     *
     * @param self self
     */
    public void onCreatingView(View self) {

    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after onDestroy()
     */
    @Override
    public void onDetach() {
        /*
         * if the fragment on detach and it's mediator and listeners will be removed
         * 如果fragment将被销毁，那么它的mediator和listener也将被移除
         */
        try {
            ((IApplication) this.getActivity().getApplication()).getMvcContext().removeMediator(
                    this);
            EventDispatcher.removeDispatcher(this.getClass().getSimpleName() + this.hashCode() + "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Please create a custom RobotlegsApplication and fill in the getMvcContextInstance() method");
        }
        super.onDetach();
    }

}

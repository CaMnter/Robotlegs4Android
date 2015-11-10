package com.camnter.robotlegs4android.base;

/**
 * Description：Event
 * Created by：CaMnter
 */
public class Event {

    public static final String ADDED_TO_STAGE = "added_to_stage";
    public static final String REMOVED_FROM_STAGE = "removed_from_stage";
    public static final String ENTER_FRAME = "enter_frame";

    private String _type;
    private Object _target = null;
    public Object data;

    public Event(String type) {
        this._type = type;
    }

    public Event(String type, Boolean bubble, Boolean cancelable) {
    }

    public String getType() {
        return this._type;
    }

    public Object getTarget() {
        return this._target;
    }

    public void setTarget(Object target) {
        this._target = target;
    }

}
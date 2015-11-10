package com.camnter.robotlegs4android.base;

/**
 * Description：ContextEvent
 * Created by：CaMnter
 */
public class ContextEvent extends Event {

    /***********************************
     * Startup
     ****************************************/

    public static final String STARTUP = "startup";

    public static final String STARTUP_COMPLETE = "startupComplete";

    /***********************************
     * Shutdown
     ***************************************/

    public static final String SHUTDOWN = "shutdown";

    public static final String SHUTDOWN_COMPLETE = "shutdownComplete";

    /************************************************************************************/

    public ContextEvent(String type) {
        super(type);
    }

    /**
     * Get The Event Type
     * 取得事件类型
     *
     * @return The Event Type 事件类型
     */
    public String getEventType() {
        return this.getType();
    }

}
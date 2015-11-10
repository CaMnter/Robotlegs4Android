package com.camnter.robotlegs4android.base;

/**
 * Description：ContextError
 * Created by：CaMnter
 */
public class ContextError extends Error {

    private static final long serialVersionUID = 1L;

    /***********************************
     * Context
     ****************************************/

    public static final String E_CONTEXT_VIEW_OVR = "Context contextView must only be set once";

    public static final String E_CONTEXT_INJECTOR = "The ContextBase does not specify a concrete IInjector. Please override the injector getter in your concrete or abstract Context.";

    public static final String E_CONTEXT_REFLECTOR = "The ContextBase does not specify a concrete IReflector. Please override the reflector getter in your concrete or abstract Context.";

    /**********************************
     * EventMap
     ****************************************/

    public static final String E_EVENTMAP_NOSNOOPING = "Listening to the context eventDispatcher is not enabled for this EventMap";

    /*********************************
     * CommandMap
     ***************************************/

    public static final String E_COMMANDMAP_OVR = "Cannot overwrite map";

    public static final String E_COMMANDMAP_NOIMPL = "Command Class does not implement an execute() method";

    /*********************************
     * MediatorMap
     **************************************/

    public static final String E_MEDIATORMAP_OVR = "Mediator Class has already been mapped to a View Class in this context";

    public static final String E_MEDIATORMAP_NOIMPL = "Mediator Class does not implement IMediator";

    /************************************************************************************/

    public ContextError(String message) {
        super(message);
    }

}
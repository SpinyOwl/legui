package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.LeguiSystemEvent;

/**
 * Event processor interface.
 * Created by Shcherbin Alexander on 6/14/2016.
 */
public abstract class SystemEventProcessor<T extends LeguiSystemEvent> {

    protected LeguiContext context;

    public SystemEventProcessor(LeguiContext context) {
        this.context = context;
    }

    /**
     * Used to process event
     *
     * @param event   event to process
     * @param mainGui mainGui element for which should be processed event
     */
    public abstract void processEvent(T event, Component mainGui);
}

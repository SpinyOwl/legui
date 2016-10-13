package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemDropEvent;

/**
 * Created by Shcherbin Alexander on 7/13/2016.
 */
public class DropEventCallback extends SystemEventProcessor<SystemDropEvent> {
    public DropEventCallback(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemDropEvent event, Component mainGui) {
        Component target = context.getMouseTargetGui();
        if (target != null) {
            target.getProcessors().getDropEventProcessor().process(target, event, context);
        } else {
            mainGui.getProcessors().getDropEventProcessor().process(mainGui, event, context);
        }
    }
}

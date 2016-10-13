package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCharModsEvent;

/**
 * Created by Alexander on 05.09.2016.
 */
public class CharModsEventProcessor extends SystemEventProcessor<SystemCharModsEvent> {

    public CharModsEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemCharModsEvent event, Component mainGui) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui != null) {
            focusedGui.getProcessors().getCharModsEventProcessor().process(focusedGui, event, context);
        }
    }
}

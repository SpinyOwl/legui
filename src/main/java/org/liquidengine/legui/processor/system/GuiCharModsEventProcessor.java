package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CharModsEvent;

/**
 * Created by Alexander on 05.09.2016.
 */
public class GuiCharModsEventProcessor extends LeguiSystemEventProcessor<CharModsEvent> {

    public GuiCharModsEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(CharModsEvent event, Component mainGui, Component target) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui != null) {
            focusedGui.getProcessors().getCharModsEventProcessor().process(focusedGui, event, context);
        }
    }
}

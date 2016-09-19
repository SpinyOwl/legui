package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CharEvent;

/**
 * Created by Alexander on 01.07.2016.
 */
public class GuiCharEventProcessor extends LeguiSystemEventProcessor<CharEvent> {

    public GuiCharEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(CharEvent event, Component mainGui, Component target) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui != null) {
            focusedGui.getProcessors().getCharEventProcessor().process(focusedGui, event, context);
        } else {
            mainGui.getProcessors().getCharEventProcessor().process(mainGui, event, context);
        }
    }

}

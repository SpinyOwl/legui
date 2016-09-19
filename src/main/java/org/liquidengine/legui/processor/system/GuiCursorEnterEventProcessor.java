package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CursorEnterEvent;

/**
 * Created by Alexander on 05.09.2016.
 */
public class GuiCursorEnterEventProcessor extends LeguiSystemEventProcessor<CursorEnterEvent> {

    public GuiCursorEnterEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(CursorEnterEvent event, Component mainGui, Component target) {
        mainGui.getProcessors().getCursorEnterEventProcessor().process(mainGui, event, context);
    }
}
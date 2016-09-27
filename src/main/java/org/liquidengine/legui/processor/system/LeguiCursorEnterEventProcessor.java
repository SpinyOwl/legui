package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CursorEnterEvent;

/**
 * Created by Alexander on 05.09.2016.
 */
public class LeguiCursorEnterEventProcessor extends LeguiSystemEventProcessor<CursorEnterEvent> {

    public LeguiCursorEnterEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(CursorEnterEvent event, Component mainGui) {
        mainGui.getProcessors().getCursorEnterEventProcessor().process(mainGui, event, context);
    }
}
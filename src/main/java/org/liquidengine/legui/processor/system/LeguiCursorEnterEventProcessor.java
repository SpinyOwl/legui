package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorEnterEvent;

/**
 * Created by Alexander on 05.09.2016.
 */
public class LeguiCursorEnterEventProcessor extends LeguiSystemEventProcessor<SystemCursorEnterEvent> {

    public LeguiCursorEnterEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemCursorEnterEvent event, Component mainGui) {
        mainGui.getProcessors().getCursorEnterEventProcessor().process(mainGui, event, context);
    }
}
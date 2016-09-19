package org.liquidengine.legui.processor.system;


import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.WindowSizeEvent;

/**
 * Created by Alexander on 01.07.2016.
 */
public class GuiWindowSizeEventProcessor extends LeguiSystemEventProcessor<WindowSizeEvent> {

    public GuiWindowSizeEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(WindowSizeEvent event, Component mainGui, Component target) {
        if (target != null) {
            target.getProcessors().getWindowSizeEventProcessor().process(target, event, context);
        } else {
            mainGui.getProcessors().getWindowSizeEventProcessor().process(target, event, context);
        }
    }
}

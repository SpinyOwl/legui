package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.KeyEvent;

/**
 * Created by Shcherbin Alexander on 7/5/2016.
 */
public class LeguiKeyEventProcessor extends LeguiSystemEventProcessor<KeyEvent> {

    public LeguiKeyEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(KeyEvent event, Component mainGui) {
        Component target = context.getMouseTargetGui();
        Component focusedGui = context.getFocusedGui();
        if (focusedGui != null) {
            focusedGui.getProcessors().getKeyEventProcessor().process(focusedGui, event, context);
        } else {
            mainGui.getProcessors().getKeyEventProcessor().process(target, event, context);
        }
    }


}

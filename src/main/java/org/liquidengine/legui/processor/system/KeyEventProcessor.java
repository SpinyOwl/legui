package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.KeyboardKeyEvent;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.processor.LeguiEventProcessor;

/**
 * Created by Shcherbin Alexander on 7/5/2016.
 */
public class KeyEventProcessor extends SystemEventProcessor<SystemKeyEvent> {

    public KeyEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemKeyEvent event, Component mainGui) {
        Component target = context.getMouseTargetGui();
        Component focusedGui = context.getFocusedGui();
        if (focusedGui != null) {
            focusedGui.getProcessors().getKeyEventProcessor().process(focusedGui, event, context);
            processEvent(focusedGui, new KeyboardKeyEvent(focusedGui, event.key, event.action, event.mods, event.scancode));
        } else {
            mainGui.getProcessors().getKeyEventProcessor().process(target, event, context);
        }
    }

    private void processEvent(Component focusedGui, KeyboardKeyEvent keyboardKeyEvent) {
        LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) {
            leguiEventProcessor.pushEvent(keyboardKeyEvent);
        } else {
            focusedGui.getListenerList().getListeners(KeyboardKeyEvent.class).forEach(l -> l.update(keyboardKeyEvent));
        }
    }


}

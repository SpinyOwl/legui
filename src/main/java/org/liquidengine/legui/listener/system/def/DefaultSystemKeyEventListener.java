package org.liquidengine.legui.listener.system.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.KeyboardKeyEvent;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.LeguiEventListenerProcessor;

/**
 * Created by Shcherbin Alexander on 11/2/2016.
 */
public class DefaultSystemKeyEventListener implements SystemEventListener<Component, SystemKeyEvent> {
    @Override
    public void update(SystemKeyEvent event, Component component, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) focusedGui = context.getMainGuiComponent();
        SystemEventListener listener = focusedGui.getSystemEventListeners().getListener(event.getClass());
        if (listener != this) {
            listener.update(event, focusedGui, context);
        }
        processEvent(focusedGui, new KeyboardKeyEvent(focusedGui, event.key, event.action, event.mods, event.scancode), context);
    }

    private void processEvent(Component focusedGui, KeyboardKeyEvent keyboardKeyEvent, LeguiContext context) {
        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) {
            leguiEventProcessor.pushEvent(keyboardKeyEvent);
        } else {
            focusedGui.getLeguiEventListeners().getListeners(KeyboardKeyEvent.class).forEach(l -> l.update(keyboardKeyEvent));
        }
    }
}

package org.liquidengine.legui.listener.system.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCharEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Shcherbin Alexander on 11/2/2016.
 */
public class DefaultSystemCharEventListener implements SystemEventListener<Component, SystemCharEvent> {
    @Override
    public void update(SystemCharEvent event, Component component, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) focusedGui = context.getMainGuiComponent();
        SystemEventListener listener = focusedGui.getSystemEventListeners().getListener(event.getClass());
        if (listener != this) {
            listener.update(event, focusedGui, context);
        }
//        processEvent(focusedGui, new KeyboardKeyEvent(focusedGui, event.key, event.action, event.mods, event.scancode), context);
    }

//    private void processEvent(Component focusedGui, KeyboardKeyEvent keyboardKeyEvent, LeguiContext context) {
//        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
//        if (leguiEventProcessor != null) {
//            leguiEventProcessor.pushEvent(keyboardKeyEvent);
//        } else {
//            focusedGui.getEventListeners().getListeners(KeyboardKeyEvent.class).forEach(l -> l.update(keyboardKeyEvent));
//        }
//    }
}

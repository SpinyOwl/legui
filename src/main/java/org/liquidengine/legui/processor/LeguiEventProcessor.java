package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.LeguiComponentEvent;
import org.liquidengine.legui.example.ExampleGui;
import org.liquidengine.legui.listener.component.LeguiListener;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public class LeguiEventProcessor {

    private Queue<LeguiComponentEvent> componentEvents = new ConcurrentLinkedQueue<>();

    public LeguiEventProcessor(ExampleGui exampleGui, LeguiContext leguiContext) {
        initialize();
    }

    private void initialize() {
    }

    public void processEvent() {
        LeguiComponentEvent event = componentEvents.poll();
        if (event != null) {
            Component component = event.getComponent();
            List<? extends LeguiListener> listenersByEvent = component.getListenerList().getListeners(event.getClass());
            for (LeguiListener leguiListener : listenersByEvent) {
                leguiListener.update(event);
            }
        }
    }

    public void pushEvent(LeguiComponentEvent event) {
        componentEvents.add(event);
    }
}

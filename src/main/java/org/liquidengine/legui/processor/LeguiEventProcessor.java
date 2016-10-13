package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.component.AbstractComponentEvent;
import org.liquidengine.legui.listener.component.IEventListener;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shcherbin Alexander on 10/5/2016.
 */
public class LeguiEventProcessor {

    private Queue<AbstractComponentEvent> componentEvents = new ConcurrentLinkedQueue<>();

    public LeguiEventProcessor() {
    }

    public void processEvent() {
        AbstractComponentEvent event = componentEvents.poll();
        if (event != null) {
            Component component = event.getComponent();
            List<? extends IEventListener> listenersByEvent = component.getListenerList().getListeners(event.getClass());
            for (IEventListener IEventListener : listenersByEvent) {
                IEventListener.update(event);
            }
        }
    }

    public void pushEvent(AbstractComponentEvent event) {
        componentEvents.add(event);
    }
}

package org.liquidengine.legui.listener;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.AbstractEvent;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class EventProcessor {
    private Queue<AbstractEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public void processEvent() {
        for (AbstractEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            Component                     component = event.getComponent();
            List<? extends EventListener> listeners = component.getListenerMap().getListeners(event.getClass());
            for (EventListener listener : listeners) {
                listener.process(event);
            }
        }
    }

    public void pushEvent(AbstractEvent event) {
        eventQueue.add(event);
    }
}

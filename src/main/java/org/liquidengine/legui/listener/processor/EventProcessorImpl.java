package org.liquidengine.legui.listener.processor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.listener.EventListener;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class EventProcessorImpl extends EventProcessor {

    private Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();

    public void processEvents() {
        for (Event event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            Component component = event.getComponent();
            List<? extends EventListener> listeners = component.getListenerMap().getListeners(event.getClass());
            for (EventListener listener : listeners) {
                listener.process(event);
            }
        }
    }

    public void pushEvent(Event event) {
        eventQueue.add(event);
    }
}

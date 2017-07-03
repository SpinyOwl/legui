package org.liquidengine.legui.listener;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.LeguiEvent;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class LeguiEventProcessor {
    private Queue<LeguiEvent> eventQueue = new ConcurrentLinkedQueue<>();

    public void processEvent() {
        for (LeguiEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            Component                          component = event.getComponent();
            List<? extends LeguiEventListener> listeners = component.getListenerMap().getListeners(event.getClass());
            for (LeguiEventListener listener : listeners) {
                listener.process(event);
            }
        }
    }

    public void pushEvent(LeguiEvent event) {
        eventQueue.add(event);
    }
}

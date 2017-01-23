package org.liquidengine.legui.event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Event queue which used to receive and store ui events
 * from system event processor
 * before they will be processed by listeners.
 */
public class EventQueue {

    private Queue<LeguiEvent> eventQueue = new ConcurrentLinkedQueue<>();

    /**
     * Used to add event to end of the queue.
     *
     * @param event event to add.
     */
    public void pushEvent(LeguiEvent event) {
        eventQueue.add(event);
    }

    /**
     * Used to retrieve head of event queue.
     *
     * @return event or null if queue is empty.
     */
    public LeguiEvent popEvent() {
        return eventQueue.poll();
    }
}

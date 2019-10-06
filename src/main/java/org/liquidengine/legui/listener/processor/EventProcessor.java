package org.liquidengine.legui.listener.processor;

import org.liquidengine.legui.event.Event;

/**
 * UI Events processor interface.
 *
 * @author ShchAlexander.
 */
public interface EventProcessor {

    /**
     * Should be called to process events.
     */
    void processEvents();

    /**
     * Used to push event to event processor.
     *
     * @param event event to push to event processor.
     */
    void pushEvent(Event event);

    /**
     * Returns true if there are events that should be processed.
     *
     * @return true if there are events that should be processed.
     */
    boolean hasEvents();
}

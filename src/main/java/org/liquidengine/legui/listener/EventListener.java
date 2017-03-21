package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.Event;

/**
 * The base event listener interface. Used to process event.
 */
public interface EventListener<E extends Event> {
    /**
     * Used to process specific event.
     *
     * @param event event to process.
     */
    void process(E event);
}

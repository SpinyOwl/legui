package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiEvent;

/**
 * The base event listener interface. Used to handle event.
 */
public interface LeguiEventListener<E extends LeguiEvent> {
    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    void process(E event);
}

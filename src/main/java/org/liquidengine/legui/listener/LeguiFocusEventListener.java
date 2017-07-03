package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiFocusEvent;

/**
 * This event listener used to handle {@link LeguiFocusEvent}.
 */
public interface LeguiFocusEventListener extends LeguiEventListener<LeguiFocusEvent> {
    /**
     * Used to handle {@link LeguiFocusEvent}.
     *
     * @param event event to handle.
     */
    void process(LeguiFocusEvent event);
}

package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.MouseClickEvent;

/**
 * Instances of this interface could be used to process {@link MouseClickEvent}.
 */
public interface MouseClickEventListener extends EventListener<MouseClickEvent> {

    /**
     * Used to process {@link MouseClickEvent}
     *
     * @param event event to process.
     */
    void process(MouseClickEvent event);
}

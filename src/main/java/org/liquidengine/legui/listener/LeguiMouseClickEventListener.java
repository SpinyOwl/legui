package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiMouseClickEvent;

/**
 * Instances of this interface could be used to handle {@link LeguiMouseClickEvent}.
 */
public interface LeguiMouseClickEventListener extends LeguiEventListener<LeguiMouseClickEvent> {

    /**
     * Used to handle {@link LeguiMouseClickEvent}
     *
     * @param event event to handle.
     */
    void process(LeguiMouseClickEvent event);
}

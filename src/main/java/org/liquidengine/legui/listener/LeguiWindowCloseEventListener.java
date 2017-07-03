package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiWindowCloseEvent;

/**
 * The listener interface that used to handle {@link LeguiWindowCloseEvent}.
 */
public interface LeguiWindowCloseEventListener extends LeguiEventListener<LeguiWindowCloseEvent> {

    /**
     * Used to handle {@link LeguiWindowCloseEvent}.
     *
     * @param event event to handle.
     */
    void process(LeguiWindowCloseEvent event);
}

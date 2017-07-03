package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiMouseDragEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public interface LeguiMouseDragEventListener extends LeguiEventListener<LeguiMouseDragEvent> {

    /**
     * Used to handle {@link LeguiMouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    void process(LeguiMouseDragEvent event);
}

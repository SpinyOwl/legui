package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.MouseDragEvent;

/**
 * Created by ShchAlexander on 2/9/2017.
 */
public interface MouseDragEventListener extends EventListener<MouseDragEvent> {

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    void process(MouseDragEvent event);
}

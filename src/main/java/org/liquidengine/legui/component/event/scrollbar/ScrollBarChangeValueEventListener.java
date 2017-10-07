package org.liquidengine.legui.component.event.scrollbar;

import org.liquidengine.legui.listener.EventListener;

/**
 * Defines contract for listener of {@link ScrollBarChangeValueEvent} event.
 */
public interface ScrollBarChangeValueEventListener extends EventListener<ScrollBarChangeValueEvent> {

    /**
     * Used to handle {@link ScrollBarChangeValueEvent} event.
     *
     * @param event event to handle.
     */
    @Override
    void process(ScrollBarChangeValueEvent event);
}

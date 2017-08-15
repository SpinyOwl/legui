package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.liquidengine.legui.component.misc.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.listener.EventListener;

/**
 * Defines contract for listener of {@link ScrollBarChangeValueEvent} event.
 */
public interface ScrollBarChangeValueEventListener extends EventListener<ScrollBarChangeValueEvent> {

    void process(ScrollBarChangeValueEvent event);
}

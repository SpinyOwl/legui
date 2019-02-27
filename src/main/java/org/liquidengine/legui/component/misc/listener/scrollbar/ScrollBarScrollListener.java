package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.ScrollEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;

import static org.liquidengine.legui.component.misc.listener.scrollbar.ScrollBarHelper.updateScrollBarValue;

/**
 * Default mouse scroll event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarScrollListener implements ScrollEventListener {

    public void process(ScrollEvent event) {
        ScrollBar scrollBar = (ScrollBar) event.getTargetComponent();
        updateScrollBarValue(event, scrollBar);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}

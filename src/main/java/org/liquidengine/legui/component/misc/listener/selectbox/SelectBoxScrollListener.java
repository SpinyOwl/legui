package org.liquidengine.legui.component.misc.listener.selectbox;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.ScrollEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * Listener of scroll action which used to scroll expanded selectbox.
 */
public class SelectBoxScrollListener implements ScrollEventListener {

    private final ScrollBar bar;

    public SelectBoxScrollListener(ScrollBar bar) {
        this.bar = bar;
    }

    @Override
    public void process(ScrollEvent event) {
        ScrollEvent<ScrollBar> newEvent
            = new ScrollEvent<>(bar, event.getContext(), event.getFrame(), event.getXoffset(), event.getYoffset());
        EventProcessorProvider.getInstance().pushEvent(newEvent);
    }
}

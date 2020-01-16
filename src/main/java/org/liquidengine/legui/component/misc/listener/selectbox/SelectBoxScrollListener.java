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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectBoxScrollListener that = (SelectBoxScrollListener) o;

        return new EqualsBuilder()
            .append(bar, that.bar)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(bar)
            .toHashCode();
    }
}

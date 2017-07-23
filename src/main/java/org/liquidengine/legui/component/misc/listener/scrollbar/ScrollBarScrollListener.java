package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.Viewport;
import org.liquidengine.legui.component.misc.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.ScrollEventListener;

/**
 * Default mouse scroll event listener for scrollbar. Generates {@link ScrollBarChangeValueEvent} event.
 */
public class ScrollBarScrollListener implements ScrollEventListener {
    public void process(ScrollEvent event) {
        ScrollBar scrollBar = (ScrollBar) event.getComponent();
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float curValue = scrollBar.getCurValue();
        float visibleAmount = scrollBar.getVisibleAmount();
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
        float newVal = (float) (curValue - scrollBar.getScrollStep() * event.getYoffset() * visibleAmount * valueRange / (valueRange - visibleAmount));

        if (newVal > maxValue) newVal = maxValue;
        if (newVal < minValue) newVal = minValue;

        event.getContext().getEventProcessor().pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, event.getContext(), curValue, newVal));
        scrollBar.setCurValue(newVal);

        Viewport viewport = scrollBar.getViewport();
        if (viewport != null) {
            viewport.updateViewport();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

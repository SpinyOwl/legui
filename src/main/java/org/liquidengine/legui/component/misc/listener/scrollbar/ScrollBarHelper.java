package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.listener.processor.EventProcessor;

public final class ScrollBarHelper {
    private ScrollBarHelper() {
    }

    public static void updateScrollBarValue(ScrollEvent event, ScrollBar scrollBar) {
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float curValue = scrollBar.getCurValue();
        float visibleAmount = scrollBar.getVisibleAmount();
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
        if (valueRange - visibleAmount < 0.001f) {
            return;
        }
        float newVal = (float) (curValue - scrollBar.getScrollStep() * event.getYoffset() * visibleAmount * valueRange / (valueRange - visibleAmount));

        if (newVal > maxValue) {
            newVal = maxValue;
        }
        if (newVal < minValue) {
            newVal = minValue;
        }

        EventProcessor.getInstance().pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, event.getContext(), event.getFrame(), curValue, newVal));
        scrollBar.setCurValue(newVal);
    }

}

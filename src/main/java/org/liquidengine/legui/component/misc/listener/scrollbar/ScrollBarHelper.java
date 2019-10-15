package org.liquidengine.legui.component.misc.listener.scrollbar;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;

public final class ScrollBarHelper {
    private ScrollBarHelper() {
    }

    public static void updateScrollBarValue(double offset, Context context, Frame frame, ScrollBar scrollBar) {
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float curValue = scrollBar.getCurValue();
        float visibleAmount = scrollBar.getVisibleAmount();
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
        if (valueRange - visibleAmount < 0.001f) {
            return;
        }
        float newVal = (float) (curValue - scrollBar.getScrollStep() * offset * visibleAmount * valueRange / (valueRange - visibleAmount));

        if (newVal > maxValue) {
            newVal = maxValue;
        }
        if (newVal < minValue) {
            newVal = minValue;
        }

        EventProcessorProvider.getInstance().pushEvent(new ScrollBarChangeValueEvent<>(scrollBar, context, frame, curValue, newVal));
        scrollBar.setCurValue(newVal);
    }

}

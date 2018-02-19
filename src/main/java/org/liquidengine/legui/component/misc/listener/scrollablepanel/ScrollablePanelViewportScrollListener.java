package org.liquidengine.legui.component.misc.listener.scrollablepanel;

import java.util.ArrayList;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.event.scrollbar.ScrollBarChangeValueEvent;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.handler.SehUtil;

/**
 * Created by ShchAlexander on 23.07.2017.
 */
public class ScrollablePanelViewportScrollListener implements EventListener<ScrollEvent> {

    /**
     * Used to handle specific event.
     *
     * @param event event to handle.
     */
    @Override
    public void process(ScrollEvent event) {
        ArrayList<Component> targetList = new ArrayList<>();
        SehUtil.recursiveTargetComponentListSearch(Mouse.getCursorPosition(), event.getTargetComponent(), targetList);
        for (Component component : targetList) {
            if (component instanceof ScrollablePanel) {
                return;
            }
        }

        ScrollablePanel scrollablePanel = (ScrollablePanel) event.getTargetComponent().getParent();
        ScrollBar scrollBar = scrollablePanel.getVerticalScrollBar();
        float maxValue = scrollBar.getMaxValue();
        float minValue = scrollBar.getMinValue();
        float curValue = scrollBar.getCurValue();
        float visibleAmount = scrollBar.getVisibleAmount();
        float valueRange = scrollBar.getMaxValue() - scrollBar.getMinValue();
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

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

package org.liquidengine.legui.processor.system.component.scrollbar;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.ScrollEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class ScrollBarScrollProcessor implements LeguiComponentEventProcessor<ScrollBar, ScrollEvent> {

    @Override
    public void process(ScrollBar gui, ScrollEvent event, LeguiContext leguiContext) {
        if (!gui.isVisible()) return;
        if (!gui.isEnabled()) return;
        if (leguiContext.getFocusedGui() != gui) return;
        float maxValue = gui.getMaxValue();
        float minValue = gui.getMinValue();
        float curValue = gui.getCurValue();
        float visibleAmount = gui.getVisibleAmount();
        float newVal = (float) (curValue - event.yoffset * visibleAmount / 10f);

        if (newVal > maxValue) newVal = maxValue;
        if (newVal < minValue) newVal = minValue;

        gui.setCurValue(newVal);
        ScrollBartUtil.updateViewport(gui, newVal);
    }

}

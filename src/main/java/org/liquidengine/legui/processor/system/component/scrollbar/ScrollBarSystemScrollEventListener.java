package org.liquidengine.legui.processor.system.component.scrollbar;

import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.Viewport;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class ScrollBarSystemScrollEventListener implements SystemEventListener<ScrollBar, SystemScrollEvent> {

    @Override
    public void update(SystemScrollEvent event, ScrollBar gui, LeguiContext leguiContext) {
        if (!gui.isVisible()) return;
        if (!gui.isEnabled()) return;

        float maxValue = gui.getMaxValue();
        float minValue = gui.getMinValue();
        float curValue = gui.getCurValue();
        float visibleAmount = gui.getVisibleAmount();
        float valueRange = gui.getMaxValue() - gui.getMinValue();
        float newVal = (float) (curValue - 0.1f * event.yoffset * visibleAmount * valueRange / (valueRange - visibleAmount));

        if (newVal > maxValue) newVal = maxValue;
        if (newVal < minValue) newVal = minValue;

        gui.setCurValue(newVal);

        Viewport viewport = gui.getViewport();
        if (viewport != null) {
            viewport.updateViewport();
        }
    }

}

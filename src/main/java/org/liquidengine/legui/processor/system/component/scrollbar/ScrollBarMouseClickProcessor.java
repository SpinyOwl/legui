package org.liquidengine.legui.processor.system.component.scrollbar;

import org.joml.Vector2f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.liquidengine.legui.util.Util;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Alexander on 25.08.2016.
 */
public class ScrollBarMouseClickProcessor implements LeguiComponentEventProcessor<ScrollBar, SystemMouseClickEvent> {
    public void process(ScrollBar gui, SystemMouseClickEvent event, LeguiContext leguiContext) {
        if (!gui.isVisible()) return;
        if (!gui.isEnabled()) return;
        boolean released = event.action != GLFW_PRESS;

        Vector2f pos = Util.calculatePosition(gui);
        Vector2f cursorPosition = leguiContext.getCursorPosition();

        float visibleAmount = gui.getVisibleAmount();
        float curValue = gui.getCurValue();
        boolean vertical = Orientation.VERTICAL.equals(gui.getOrientation());

        Vector2f guiSize = gui.getSize();
        float arrowSize = gui.isArrowsEnabled() ? gui.getArrowSize() : 0;
        float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
        float maxValue = gui.getMaxValue();
        float minValue = gui.getMinValue();
        float valueRange = maxValue - minValue;
        float barSize = scrollBarSize * visibleAmount / valueRange;
        if (barSize < ScrollBar.MIN_SCROLL_SIZE) barSize = ScrollBar.MIN_SCROLL_SIZE;
        float scrollPosAccordingToScrollBounds = (scrollBarSize - barSize) * curValue / valueRange;

        float left, curPos, newVal;
        if (vertical) {
            left = pos.y + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.y;
        } else {
            left = pos.x + scrollPosAccordingToScrollBounds + arrowSize;
            curPos = cursorPosition.x;
        }
        if (curPos < left) {
            newVal = curValue - visibleAmount;
            if (!released) updateViewport(gui, maxValue, minValue, newVal);
            gui.setScrolling(false);
        } else if (curPos > left + barSize) {
            newVal = curValue + visibleAmount;
            if (!released) updateViewport(gui, maxValue, minValue, newVal);
            gui.setScrolling(false);
        } else {
            if (released) gui.setScrolling(false);
            else {
                gui.setScrolling(true);
            }
        }
    }

    private void updateViewport(ScrollBar gui, float maxValue, float minValue, float newVal) {
        if (newVal > maxValue) newVal = maxValue;
        else if (newVal < minValue) newVal = minValue;
        gui.setCurValue(newVal);
        ScrollBartUtil.updateViewport(gui, newVal);
    }

}

package org.liquidengine.legui.processor.system.component.scrollbar;

import org.joml.Vector2f;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.optional.Orientation;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.lwjgl.glfw.GLFW;

import static org.liquidengine.legui.component.ScrollBar.MIN_SCROLL_SIZE;
import static org.liquidengine.legui.processor.system.component.scrollbar.ScrollBartUtil.updateViewport;
import static org.liquidengine.legui.util.Util.calculatePosition;

/**
 * Created by Alexander on 26.08.2016.
 */
public class ScrollBarCursorPosListener implements SystemEventListener<ScrollBar, SystemCursorPosEvent> {
    public void update(SystemCursorPosEvent event, ScrollBar gui, LeguiContext leguiContext) {
        if (!gui.isVisible()) return;
        if (!gui.isEnabled()) return;
        if (leguiContext.getFocusedGui() != gui) return;
        if (!leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) return;
        if (!gui.isScrolling()) return;

        Vector2f pos = calculatePosition(gui);
        Vector2f cursorPosition = leguiContext.getCursorPosition();

        float visibleAmount = gui.getVisibleAmount();
        boolean vertical = Orientation.VERTICAL.equals(gui.getOrientation());

        Vector2f guiSize = gui.getSize();
        float arrowSize = gui.isArrowsEnabled() ? gui.getArrowSize() : 0;
        float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
        float maxValue = gui.getMaxValue();
        float minValue = gui.getMinValue();
        float valueRange = maxValue - minValue;
        float barSize = scrollBarSize * visibleAmount / valueRange;
        if (barSize < MIN_SCROLL_SIZE) barSize = MIN_SCROLL_SIZE;

        float curPos, dpos;
        if (vertical) {
            dpos = pos.y;
            curPos = cursorPosition.y;
        } else {
            dpos = pos.x;
            curPos = cursorPosition.x;
        }
        float newVal = valueRange * (curPos - (dpos + arrowSize + barSize / 2f)) / (scrollBarSize - barSize);
        if (newVal > maxValue) newVal = maxValue;
        else if (newVal < minValue) newVal = minValue;
        gui.setCurValue(newVal);
        updateViewport(gui, newVal);
    }
}

//package org.liquidengine.legui.processor.system.component.scrollbar;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.element.Orientation;
//import org.liquidengine.legui.element.ScrollBar;
//import org.liquidengine.legui.event.system.CursorPosEvent;
//import org.liquidengine.legui.processor.element.IGuiProcessor;
//import org.liquidengine.legui.processor.system.GuiEventProcessorState;
//import org.lwjgl.glfw.GLFW;
//
//import static org.liquidengine.legui.element.ScrollBar.MIN_SCROLL_SIZE;
//import static org.liquidengine.legui.processor.element.scrollbar.ScrollBartUtil.updateViewport;
//import static org.liquidengine.legui.util.Util.calculatePosition;
//
///**
// * Created by Alexander on 26.08.2016.
// */
//public class ScrollBarCursorPosProcessor implements IGuiProcessor<ScrollBar, CursorPosEvent> {
//    public void process(ScrollBar gui, CursorPosEvent event, GuiEventProcessorState state) {
//        if (!gui.isVisible()) return;
//        if (!gui.isEnabled()) return;
//        if (state.getFocusedGui() != gui) return;
//        if (!state.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) return;
//
//        Vector2f pos = calculatePosition(gui);
//        Vector2f cursorPosition = state.getCursorPosition();
//
//        float visibleAmount = gui.getVisibleAmount();
//        boolean vertical = Orientation.VERTICAL.equals(gui.getOrientation());
//
//        Vector2f guiSize = gui.getSize();
//        float arrowSize = gui.isArrowsEnabled() ? gui.getArrowSize() : 0;
//        float scrollBarSize = (vertical ? guiSize.y : guiSize.x) - 2 * arrowSize;
//        float maxValue = gui.getMaxValue();
//        float minValue = gui.getMinValue();
//        float valueRange = maxValue - minValue;
//        float barSize = scrollBarSize * visibleAmount / valueRange;
//        if (barSize < MIN_SCROLL_SIZE) barSize = MIN_SCROLL_SIZE;
//
//        float curPos, dpos;
//        if (vertical) {
//            dpos = pos.y;
//            curPos = cursorPosition.y;
//        } else {
//            dpos = pos.x;
//            curPos = cursorPosition.x;
//        }
//        if (gui.isScrolling()) {
//            float newVal = valueRange * (curPos - (dpos + arrowSize + barSize / 2f)) / (scrollBarSize - barSize);
//            if (newVal > maxValue) newVal = maxValue;
//            else if (newVal < minValue) newVal = minValue;
//            gui.setCurValue(newVal);
//            updateViewport(gui, newVal);
//        }
//    }
//}

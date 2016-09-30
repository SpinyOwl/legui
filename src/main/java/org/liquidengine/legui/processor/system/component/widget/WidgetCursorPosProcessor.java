package org.liquidengine.legui.processor.system.component.widget;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.CursorPosEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 28.08.2016.
 */
public class WidgetCursorPosProcessor implements LeguiComponentEventProcessor<Widget, CursorPosEvent> {
    @Override
    public void process(Widget gui, CursorPosEvent event, LeguiContext processorState) {
        Vector2f cursorPosition = processorState.getCursorPosition();
        Vector2f cursorPositionPrev = processorState.getCursorPositionPrev();
        if (gui.isVisible() && processorState.getFocusedGui() == gui && processorState.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
            Vector2f delta = new Vector2f(cursorPosition).sub(cursorPositionPrev);
            Vector2f widP = gui.getPosition();
            widP.add(delta);
        }
    }
}

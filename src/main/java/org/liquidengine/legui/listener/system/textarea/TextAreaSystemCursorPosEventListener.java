package org.liquidengine.legui.listener.system.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 19.10.2016.
 */
public class TextAreaSystemCursorPosEventListener implements SystemEventListener<TextArea, SystemCursorPosEvent> {
    @Override
    public void update(SystemCursorPosEvent event, TextArea textArea, LeguiContext leguiContext) {
        if (textArea == leguiContext.getFocusedGui() && leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
            int mouseCaretPosition = textArea.getMouseCaretPosition();
            textArea.setCaretPosition(mouseCaretPosition);
            textArea.setEndSelectionIndex(mouseCaretPosition);
        }
    }
}

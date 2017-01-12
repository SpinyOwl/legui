package org.liquidengine.legui.listener.system.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.system.def.DefaultSystemCursorPosEventListener;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 19.10.2016.
 */
public class TextInputSystemCursorPosEventListener implements SystemEventListener<TextInput, SystemCursorPosEvent> {
    private DefaultSystemCursorPosEventListener defaultSystemCursorPosEventListener = new DefaultSystemCursorPosEventListener();

    @Override
    public void update(SystemCursorPosEvent event, TextInput textInput, LeguiContext leguiContext) {
        defaultSystemCursorPosEventListener.update(event,textInput,leguiContext);
        if (textInput == leguiContext.getFocusedGui() && leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
            int mouseCaretPosition = textInput.getMouseCaretPosition();
            textInput.setCaretPosition(mouseCaretPosition);
            textInput.setEndSelectionIndex(mouseCaretPosition);
        }
    }
}

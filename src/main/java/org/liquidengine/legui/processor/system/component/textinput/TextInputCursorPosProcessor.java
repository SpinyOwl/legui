package org.liquidengine.legui.processor.system.component.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 19.10.2016.
 */
public class TextInputCursorPosProcessor implements LeguiComponentEventProcessor<TextInput, SystemCursorPosEvent> {
    @Override
    public void process(TextInput textInput, SystemCursorPosEvent event, LeguiContext leguiContext) {
        if (textInput == leguiContext.getFocusedGui() && leguiContext.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
            textInput.setCaretPosition(textInput.getMouseCaretPosition());
        }
    }
}

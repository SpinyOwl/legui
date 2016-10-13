package org.liquidengine.legui.processor.system.component.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputKeyProcessor implements LeguiComponentEventProcessor<TextInput, SystemKeyEvent> {
    @Override
    public void process(TextInput gui, SystemKeyEvent event, LeguiContext leguiContext) {
        if (gui.isFocused() && gui.isEditable()) {
            int key = event.key;
            int caretPosition = gui.getCaretPosition();
            if (key == GLFW_KEY_LEFT && event.action != GLFW_RELEASE) {
                if (caretPosition > 0) {
                    gui.setCaretPosition(caretPosition - 1);
                }
            } else {
                TextState textState = gui.getTextState();
                if (key == GLFW_KEY_RIGHT && event.action != GLFW_RELEASE) {
                    if (caretPosition < textState.length()) {
                        gui.setCaretPosition(caretPosition + 1);
                    }
                } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && event.action != GLFW_RELEASE) {
                    gui.setCaretPosition(0);
                } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && event.action != GLFW_RELEASE) {
                    gui.setCaretPosition(gui.getTextState().length());
                } else if (key == GLFW_KEY_BACKSPACE && event.action != GLFW_RELEASE) {
                    if (caretPosition != 0) {
                        textState.deleteCharAt(caretPosition - 1);
                        gui.setCaretPosition(caretPosition - 1);
                    }
                } else if (key == GLFW_KEY_DELETE && event.action != GLFW_RELEASE) {
                    if (caretPosition != textState.length()) {
                        textState.deleteCharAt(caretPosition);
                    }
                }
            }
        }

    }
}

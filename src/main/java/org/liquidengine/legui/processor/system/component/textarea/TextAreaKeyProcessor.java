package org.liquidengine.legui.processor.system.component.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextAreaKeyProcessor implements LeguiComponentEventProcessor<TextArea, SystemKeyEvent> {
    @Override
    public void process(TextArea gui, SystemKeyEvent event, LeguiContext leguiContext) {
        if (gui.isFocused() && gui.isEditable()) {
            int key = event.key;
            int caretPosition = gui.getCaretPosition();
            boolean PRESS = event.action != GLFW_RELEASE;
            if (key == GLFW_KEY_LEFT && PRESS) {
                if (caretPosition > 0) {
                    gui.setCaretPosition(caretPosition - 1);
                }
            } else {
                TextState textState = gui.getTextState();
                if (key == GLFW_KEY_RIGHT && PRESS) {
                    if (caretPosition < textState.length()) {
                        gui.setCaretPosition(caretPosition + 1);
                    }
                } else if (key == GLFW_KEY_HOME && PRESS) {
                    gui.setCaretPosition(0);
                } else if (key == GLFW_KEY_END && PRESS) {
                    gui.setCaretPosition(gui.getTextState().length());
                } else if (key == GLFW_KEY_ENTER && PRESS) {
                    if (gui.isEditable()) {
                        gui.getTextState().insert(caretPosition, "\n");
                        gui.setCaretPosition(caretPosition + 1);
                    }
                } else if (key == GLFW_KEY_BACKSPACE && PRESS) {
                    if (gui.isEditable() && caretPosition != 0) {
                        textState.deleteCharAt(caretPosition - 1);
                        gui.setCaretPosition(caretPosition - 1);
                    }
                } else if (key == GLFW_KEY_DELETE && PRESS) {
                    if (gui.isEditable() && caretPosition != textState.length()) {
                        textState.deleteCharAt(caretPosition);
                    }
                }
            }
        }

    }
}

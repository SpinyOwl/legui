package org.liquidengine.legui.listener.system.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputSystemKeyEventProcessor implements SystemEventListener<TextInput, SystemKeyEvent> {
    @Override
    public void update(SystemKeyEvent event, TextInput gui, LeguiContext leguiContext) {
        if (gui.isFocused() && gui.isEditable()) {
            int key = event.key;
            int caretPosition = gui.getCaretPosition();
            boolean pressed = event.action != GLFW_RELEASE;
            if (key == GLFW_KEY_LEFT && pressed) {
                if (caretPosition > 0) {
                    gui.setCaretPosition(caretPosition - 1);
                }
            } else {
                TextState textState = gui.getTextState();
                if (key == GLFW_KEY_RIGHT && pressed) {
                    if (caretPosition < textState.length()) {
                        gui.setCaretPosition(caretPosition + 1);
                    }
                } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && pressed) {
                    gui.setCaretPosition(0);
                } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && pressed) {
                    gui.setCaretPosition(gui.getTextState().length());
                } else if (key == GLFW_KEY_BACKSPACE && pressed) {
                    if (caretPosition != 0) {
                        textState.deleteCharAt(caretPosition - 1);
                        gui.setCaretPosition(caretPosition - 1);
                    }
                } else if (key == GLFW_KEY_DELETE && pressed) {
                    if (caretPosition != textState.length()) {
                        textState.deleteCharAt(caretPosition);
                    }
                } else if (key == GLFW_KEY_V && pressed && event.mods == GLFW_MOD_CONTROL) {
                    String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
                    if (s != null) {
                        textState.insert(caretPosition, s);
                        gui.setCaretPosition(caretPosition + s.length());
                    }
                }
            }
        }

    }
}

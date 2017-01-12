package org.liquidengine.legui.listener.system.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemKeyEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Alexander on 28.08.2016.
 */
public class TextInputSystemKeyEventProcessor implements SystemEventListener<TextInput, SystemKeyEvent> {

    @Override
    public void update(SystemKeyEvent event, TextInput gui, LeguiContext leguiContext) {
        if (!gui.getState().isFocused() || !gui.isEditable()) return;

        int       key           = event.key;
        int       caretPosition = gui.getCaretPosition();
        boolean   pressed       = event.action != GLFW_RELEASE;
        TextState textState     = gui.getTextState();
        if (key == GLFW_KEY_LEFT && pressed) {
            keyLeftAction(gui, caretPosition, event.mods);
        } else if (key == GLFW_KEY_RIGHT && pressed) {
            keyRightAction(gui, caretPosition, textState, event.mods);
        } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && pressed) {
            keyUpAndHomeAction(gui, event.mods);
        } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && pressed) {
            keyDownAndEndAction(gui, event.mods);
        } else if (key == GLFW_KEY_BACKSPACE && pressed) {
            keyBackSpaceAction(gui, caretPosition, textState, event.mods);
        } else if (key == GLFW_KEY_DELETE && pressed) {
            keyDeleteAction(gui, caretPosition, textState, event.mods);
        } else if (key == GLFW_KEY_V && pressed && event.mods == GLFW_MOD_CONTROL) {
            pasteAction(gui, leguiContext, caretPosition, textState);
        } else if (key == GLFW_KEY_C && pressed && event.mods == GLFW_MOD_CONTROL) {
            copyAction(gui, leguiContext);
        } else if (key == GLFW_KEY_X && pressed && event.mods == GLFW_MOD_CONTROL) {
            cutAction(gui, leguiContext);
        }
    }

    private void cutAction(TextInput gui, LeguiContext leguiContext) {
        if (gui.isEditable()) {
            String s = gui.getSelection();
            if (s != null) {
                int start = gui.getStartSelectionIndex();
                int end   = gui.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                gui.getTextState().delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
            }
        }
    }

    private void copyAction(TextInput gui, LeguiContext leguiContext) {
        String s = gui.getSelection();
        if (s != null) glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
    }

    private void pasteAction(TextInput gui, LeguiContext leguiContext, int caretPosition, TextState textState) {
        if (gui.isEditable()) {
            String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
            if (s != null) {
                textState.insert(caretPosition, s);
                gui.setCaretPosition(caretPosition + s.length());
            }
        }
    }

    private void keyDeleteAction(TextInput gui, int caretPosition, TextState textState, int mods) {
        if (gui.isEditable()) {
            if ((mods & GLFW_MOD_CONTROL) != 0) {
                gui.setEndSelectionIndex(findNextWord(textState.getText(), caretPosition));
            }
            int start = gui.getStartSelectionIndex();
            int end   = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (start == end && caretPosition != textState.length()) {
                textState.deleteCharAt(caretPosition);
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    private void keyBackSpaceAction(TextInput gui, int caretPosition, TextState textState, int mods) {
        if (gui.isEditable()) {
            if ((mods & GLFW_MOD_CONTROL) != 0) {
                gui.setEndSelectionIndex(findPrevWord(textState.getText(), caretPosition));
            }
            int start = gui.getStartSelectionIndex();
            int end   = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            if (caretPosition != 0) {
                if (start == end) {
                    textState.deleteCharAt(caretPosition - 1);
                    gui.setCaretPosition(caretPosition - 1);
                } else {
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                }
            }
        }
    }

    private void keyDownAndEndAction(TextInput gui, int mods) {
        int newCaretPosition = gui.getTextState().length();
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);

    }

    private void keyUpAndHomeAction(TextInput gui, int mods) {
        int newCaretPosition = 0;
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyRightAction(TextInput gui, int caretPosition, TextState textState, int mods) {
        if (caretPosition < textState.length()) {
            int newCaretPosition = caretPosition + 1;
            if ((mods & GLFW_MOD_CONTROL) != 0) {
                newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
            }

            gui.setEndSelectionIndex(newCaretPosition);

            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }

            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyLeftAction(TextInput gui, int caretPosition, int mods) {
        if (caretPosition > 0) {
            int newCaretPosition = caretPosition - 1;
            if ((mods & GLFW_MOD_CONTROL) != 0) {
                newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
            }

            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }
    }

}

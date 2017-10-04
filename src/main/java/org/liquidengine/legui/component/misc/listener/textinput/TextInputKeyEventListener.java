package org.liquidengine.legui.component.misc.listener.textinput;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.event.textinput.TextInputContentChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.context.Context;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextInputKeyEventListener implements KeyEventListener {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        TextInput gui = (TextInput) event.getComponent();
        int key = event.getKey();
        boolean pressed = event.getAction() != GLFW_RELEASE;
        if (key == GLFW_KEY_LEFT && pressed) {
            keyLeftAction(gui, event.getMods());
        } else if (key == GLFW_KEY_RIGHT && pressed) {
            keyRightAction(gui, event.getMods());
        } else if ((key == GLFW_KEY_UP || key == GLFW_KEY_HOME) && pressed) {
            keyUpAndHomeAction(gui, event.getMods());
        } else if ((key == GLFW_KEY_DOWN || key == GLFW_KEY_END) && pressed) {
            keyDownAndEndAction(gui, event.getMods());
        } else if (key == GLFW_KEY_BACKSPACE && pressed) {
            keyBackSpaceAction(gui, event.getContext(), event.getFrame(), event.getMods());
        } else if (key == GLFW_KEY_DELETE && pressed) {
            keyDeleteAction(gui, event.getContext(), event.getFrame(), event.getMods());
        } else if (key == GLFW_KEY_V && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            pasteAction(gui, event.getContext(), event.getFrame());
        } else if (key == GLFW_KEY_C && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            copyAction(gui, event.getContext());
        } else if (key == GLFW_KEY_X && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            cutAction(gui, event.getContext(), event.getFrame());
        } else if (key == GLFW_KEY_A && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            selectAllAction(gui);
        }
    }

    /**
     * Selects all text.
     *
     * @param gui text input to work with.
     */
    private void selectAllAction(TextInput gui) {
        TextState textState = gui.getTextState();
        gui.setStartSelectionIndex(0);
        gui.setEndSelectionIndex(textState.length());
        gui.setCaretPosition(textState.length());
    }

    /**
     * Used to cut some string from text input and put it to clipboard.
     *
     * @param gui gui to work with.
     * @param leguiContext context.
     * @param frame frame
     */
    private void cutAction(TextInput gui, Context leguiContext, Frame frame) {
        if (gui.isEditable()) {
            String s = gui.getSelection();
            if (s != null) {
                int start = gui.getStartSelectionIndex();
                int end = gui.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                String oldText = gui.getTextState().getText();
                gui.getTextState().delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                String newText = gui.getTextState().getText();
                EventProcessor.getInstance().pushEvent(new TextInputContentChangeEvent(gui, leguiContext, frame, oldText, newText));
                glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
            }
        } else {
            copyAction(gui, leguiContext);
        }
    }

    /**
     * Used to copy selected text to clipboard.
     *
     * @param gui gui.
     * @param leguiContext context.
     */
    private void copyAction(TextInput gui, Context leguiContext) {
        String s = gui.getSelection();
        if (s != null) {
            glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
        }
    }

    /**
     * Used to paste clipboard data to gui element.
     *
     * @param gui gui to paste
     * @param leguiContext context.
     * @param frame frame
     */
    private void pasteAction(TextInput gui, Context leguiContext, Frame frame) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
            if (s != null) {
                String oldText = textState.getText();
                textState.insert(caretPosition, s);
                gui.setCaretPosition(caretPosition + s.length());
                String newText = textState.getText();
                EventProcessor.getInstance().pushEvent(new TextInputContentChangeEvent(gui, leguiContext, frame, oldText, newText));
            }
        }
    }

    /**
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextInput gui, Context leguiContext, Frame frame, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            String oldText = textState.getText();
            if (start == end && caretPosition != textState.length()) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    end = findNextWord(textState.getText(), caretPosition);
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    textState.deleteCharAt(caretPosition);
                    gui.setStartSelectionIndex(caretPosition);
                    gui.setEndSelectionIndex(caretPosition);
                }
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
            String newText = textState.getText();
            EventProcessor.getInstance().pushEvent(new TextInputContentChangeEvent(gui, leguiContext, frame, oldText, newText));
        }
    }

    /**
     * Backspace action. Deletes selected text or symbol before caret or words before caret.
     *
     * @param gui gui to remove text data.
     * @param mods key mods.
     */
    private void keyBackSpaceAction(TextInput gui, Context leguiContext, Frame frame, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
            String oldText = textState.getText();
            if (start == end && caretPosition != 0) {
                if ((mods & GLFW_MOD_CONTROL) != 0) {
                    start = findPrevWord(textState.getText(), caretPosition);
                    textState.delete(start, end);
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    int newCaretPosition = caretPosition - 1;
                    textState.deleteCharAt(newCaretPosition);
                    gui.setCaretPosition(newCaretPosition);
                    gui.setStartSelectionIndex(newCaretPosition);
                    gui.setEndSelectionIndex(newCaretPosition);
                }
            } else {
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
            String newText = textState.getText();
            EventProcessor.getInstance().pushEvent(new TextInputContentChangeEvent(gui, leguiContext, frame, oldText, newText));
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

    private void keyRightAction(TextInput gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition + 1;
        // reset if out of bounds
        if (newCaretPosition >= textState.length()) {
            newCaretPosition = textState.length();
        }
        if ((mods & GLFW_MOD_CONTROL) != 0) {
            newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
        }

        gui.setEndSelectionIndex(newCaretPosition);

        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }

        gui.setCaretPosition(newCaretPosition);
    }

    private void keyLeftAction(TextInput gui, int mods) {
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition - 1;
        // reset if out of bounds.
        if (newCaretPosition <= 0) {
            newCaretPosition = 0;
        }
        if ((mods & GLFW_MOD_CONTROL) != 0) {
            newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
        }
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof TextInputKeyEventListener;
    }
}

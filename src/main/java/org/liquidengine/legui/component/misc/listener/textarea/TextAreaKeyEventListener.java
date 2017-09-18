package org.liquidengine.legui.component.misc.listener.textarea;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DELETE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_HOME;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER;
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

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.system.context.Context;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextAreaKeyEventListener implements KeyEventListener {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        TextArea textArea = (TextArea) event.getComponent();
        int key = event.getKey();
        boolean pressed = event.getAction() != GLFW_RELEASE;
        if (key == GLFW_KEY_LEFT && pressed) {
            keyLeftAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_RIGHT && pressed) {
            keyRightAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_UP && pressed) {
            keyUpAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_DOWN && pressed) {
            keyDownAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_HOME && pressed) {
            keyHomeAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_END && pressed) {
            keyEndAction(textArea, event.getMods());
        } else if ((key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) && pressed) {
            keyEnterAction(textArea);
        } else if (key == GLFW_KEY_BACKSPACE && pressed) {
            keyBackSpaceAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_DELETE && pressed) {
            keyDeleteAction(textArea, event.getMods());
        } else if (key == GLFW_KEY_V && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            pasteAction(textArea, event.getContext());
        } else if (key == GLFW_KEY_C && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            copyAction(textArea, event.getContext());
        } else if (key == GLFW_KEY_X && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            cutAction(textArea, event.getContext());
        } else if (key == GLFW_KEY_A && pressed && event.getMods() == GLFW_MOD_CONTROL) {
            selectAllAction(textArea);
        }
    }

    /**
     * Selects all text.
     *
     * @param gui text area to work with.
     */
    private void selectAllAction(TextArea gui) {
        TextState textState = gui.getTextState();
        gui.setStartSelectionIndex(0);
        gui.setEndSelectionIndex(textState.length());
        gui.setCaretPosition(textState.length());
    }

    /**
     * Used to cut some string from text area and put it to clipboard.
     *
     * @param gui text area to work with.
     * @param leguiContext context.
     */
    private void cutAction(TextArea gui, Context leguiContext) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            String s = gui.getSelection();
            if (s != null) {
                int start = gui.getStartSelectionIndex();
                int end = gui.getEndSelectionIndex();
                if (start > end) {
                    int swap = start;
                    start = end;
                    end = swap;
                }
                textState.delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                glfwSetClipboardString(leguiContext.getGlfwWindow(), s);
            }
        }
    }

    /**
     * Used to copy selected text to clipboard.
     *
     * @param gui gui.
     * @param leguiContext context.
     */
    private void copyAction(TextArea gui, Context leguiContext) {
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
     */
    private void pasteAction(TextArea gui, Context leguiContext) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            String s = glfwGetClipboardString(leguiContext.getGlfwWindow());
            if (s != null) {
                textState.insert(caretPosition, s);
                gui.setCaretPosition(caretPosition + s.length());
            }
        }
    }

    /**
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextArea gui, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
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
        }
    }

    /**
     * Backspace action. Deletes selected text or symbol before caret or words before caret.
     *
     * @param gui gui to remove text data.
     * @param mods key mods.
     */
    private void keyBackSpaceAction(TextArea gui, int mods) {
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start > end) {
                start = gui.getEndSelectionIndex();
                end = gui.getStartSelectionIndex();
            }
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
        }
    }

    /**
     * Action on Enter key. Adds new line symbol on caret position.
     *
     * @param gui gui to add new line.
     */
    private void keyEnterAction(TextArea gui) {
        if (gui.isEditable()) {
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            if (start != end) {
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }
                gui.getTextState().delete(start, end);
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }

            int caretPosition = gui.getCaretPosition();
            gui.getTextState().insert(caretPosition, "\n");
            int newCaretPosition = caretPosition + 1;
            gui.setStartSelectionIndex(newCaretPosition);
            gui.setEndSelectionIndex(newCaretPosition);
            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyEndAction(TextArea gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData currentLine = getStartLineIndexAndLineNumber(lines, caretPosition);
        int cl = lines[currentLine.lineIndex].length();
        int delta = cl - currentLine.caretPositionInLine;

        int newCaretPosition = caretPosition + delta;
        gui.setEndSelectionIndex(newCaretPosition);
        //update start selection index if SHIFT was not pressed.
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyHomeAction(TextArea gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);

        int newCaretPosition = caretPosition - some.caretPositionInLine;
        gui.setEndSelectionIndex(newCaretPosition);
        //update start selection index if SHIFT was not pressed.
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyDownAction(TextArea gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        if (caretPosition < textState.length()) {
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
            int newCaretPosition = text.length();

            if (some.lineIndex < lines.length - 1) {
                int nl = lines[some.lineIndex + 1].length() + 1;
                int cl = lines[some.lineIndex].length() + 1;
                if (some.caretPositionInLine >= nl - 1) {
                    newCaretPosition = caretPosition + nl + cl - 1 - some.caretPositionInLine;
                } else {
                    newCaretPosition = caretPosition + cl;
                }
            }
            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyUpAction(TextArea gui, int mods) {
        int caretPosition = gui.getCaretPosition();
        if (caretPosition > 0) {
            TextState textState = gui.getTextState();
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);
            int newCaretPosition = 0;
            if (some.lineIndex > 0) {
                int nl = lines[some.lineIndex - 1].length() + 1;
                newCaretPosition = caretPosition - (some.caretPositionInLine >= nl - 1 ? some.caretPositionInLine + 1 : nl);
            }

            gui.setEndSelectionIndex(newCaretPosition);
            if ((mods & GLFW_MOD_SHIFT) == 0) {
                gui.setStartSelectionIndex(newCaretPosition);
            }
            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyRightAction(TextArea gui, int mods) {
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

    private void keyLeftAction(TextArea gui, int mods) {
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

    private LineData getStartLineIndexAndLineNumber(String[] lines, int caretPosition) {
        int caretLine = 0;
        int caretOffset = 0;
        for (String line : lines) {
            int newOffset = caretOffset + line.length();
            if (newOffset >= caretPosition) {
                break;
            }
            caretLine++;
            caretOffset = newOffset + 1;
        }
        return new LineData(caretPosition - caretOffset, caretLine);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }

    private static class LineData {

        private int caretPositionInLine;
        private int lineIndex;

        private LineData(int caretPositionInLine, int lineIndex) {
            this.caretPositionInLine = caretPositionInLine;
            this.lineIndex = lineIndex;
        }
    }
}

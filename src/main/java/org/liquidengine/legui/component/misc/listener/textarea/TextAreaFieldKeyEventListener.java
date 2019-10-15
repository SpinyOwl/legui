package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.listener.KeyEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.Clipboard;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextAreaFieldKeyEventListener implements KeyEventListener {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyEvent event) {
        if (
            event.getKey() == GLFW_KEY_LEFT_SHIFT ||
                event.getKey() == GLFW_KEY_RIGHT_SHIFT ||
                event.getKey() == GLFW_KEY_LEFT_CONTROL ||
                event.getKey() == GLFW_KEY_RIGHT_CONTROL ||
                event.getKey() == GLFW_KEY_LEFT_ALT ||
                event.getKey() == GLFW_KEY_RIGHT_ALT
        )
            return;

        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        boolean pressed = event.getAction() != GLFW_RELEASE;

        if (!pressed) {
            EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
            return;
        }

        processKey(textAreaField, event);
        EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
    }

    private void processKey(TextAreaField textAreaField, KeyEvent event) {
        int key = event.getKey();
        int mods = event.getMods();
        if (key == GLFW_KEY_LEFT) {
            keyLeftAction(textAreaField, mods);
        } else if (key == GLFW_KEY_RIGHT) {
            keyRightAction(textAreaField, mods);
        } else if (key == GLFW_KEY_UP) {
            keyUpAction(textAreaField, mods);
        } else if (key == GLFW_KEY_DOWN) {
            keyDownAction(textAreaField, mods);
        } else if (key == GLFW_KEY_HOME) {
            keyHomeAction(textAreaField, mods);
        } else if (key == GLFW_KEY_END) {
            keyEndAction(textAreaField, mods);
        } else if (key == GLFW_KEY_ENTER || key == GLFW_KEY_KP_ENTER) {
            keyEnterAction(textAreaField);
        } else if (key == GLFW_KEY_BACKSPACE) {
            keyBackSpaceAction(textAreaField, mods);
        } else if (key == GLFW_KEY_DELETE) {
            keyDeleteAction(textAreaField, mods);
        } else if (key == GLFW_KEY_V) {
            pasteAction(textAreaField, mods);
        } else if (key == GLFW_KEY_C) {
            copyAction(textAreaField, mods);
        } else if (key == GLFW_KEY_X) {
            cutAction(textAreaField, mods);
        } else if (key == GLFW_KEY_A) {
            selectAllAction(textAreaField, mods);
        } else if (key == GLFW_KEY_TAB) {
            addTab(textAreaField, mods);
        }
    }

    private boolean isControlPressed(int mods) {
        return (mods & GLFW_MOD_CONTROL) != 0;
    }

    /**
     * Used to insert '\t' symbol.
     *
     * @param textAreaField text area to work with.
     * @param mods
     */
    private void addTab(TextAreaField textAreaField, int mods) {
        if (isControlPressed(mods)) {
            return;
        }
        if (textAreaField.isEditable()) {
            int oldCPos = textAreaField.getCaretPosition();
            TextState textState = textAreaField.getTextState();
            StringBuilder t = new StringBuilder(textState.getText());
            t.insert(oldCPos, "\t");
            textState.setText(t.toString());
            int caretPosition = oldCPos + 1;
            textAreaField.setCaretPosition(caretPosition);
            textAreaField.setStartSelectionIndex(caretPosition);
            textAreaField.setEndSelectionIndex(caretPosition);
        }
    }

    /**
     * Selects all text.
     *
     * @param gui  text area to work with.
     * @param mods
     */
    private void selectAllAction(TextAreaField gui, int mods) {
        if (!isControlPressed(mods)) {
            return;
        }
        TextState textState = gui.getTextState();
        gui.setStartSelectionIndex(0);
        gui.setEndSelectionIndex(textState.length());
        gui.setCaretPosition(textState.length());
    }

    /**
     * Used to cut some string from text area and put it to clipboard.
     *
     * @param gui  text area to work with.
     * @param mods
     */
    private void cutAction(TextAreaField gui, int mods) {
        if (!isControlPressed(mods)) {
            return;
        }
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
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
                Clipboard.getInstance().setClipboardString(s);
            }
        }
    }

    /**
     * Used to copy selected text to clipboard.
     *
     * @param gui  gui.
     * @param mods
     */
    private void copyAction(TextAreaField gui, int mods) {
        if (!isControlPressed(mods)) {
            return;
        }
        String s = gui.getSelection();
        if (s != null) {
            Clipboard.getInstance().setClipboardString(s);
        }
    }

    /**
     * Used to paste clipboard data to gui element.
     *
     * @param gui  gui to paste
     * @param mods
     */
    private void pasteAction(TextAreaField gui, int mods) {
        if (!isControlPressed(mods)) {
            return;
        }
        if (gui.isEditable()) {
            TextState textState = gui.getTextState();
            int caretPosition = gui.getCaretPosition();
            String s = Clipboard.getInstance().getClipboardString();
            if (s != null) {

                int start = gui.getStartSelectionIndex();
                int end = gui.getEndSelectionIndex();
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }
                if (start != end) {
                    StringBuilder t = new StringBuilder(textState.getText());
                    t.delete(start, end);
                    textState.setText(t.toString());
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                    caretPosition = start;
                }

                StringBuilder builder = new StringBuilder(textState.getText());
                builder.insert(caretPosition, s);
                textState.setText(builder.toString());
                gui.setCaretPosition(caretPosition + s.length());
            }
        }
    }

    /**
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui  gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextAreaField gui, int mods) {
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
                if (isControlPressed(mods)) {
                    end = findNextWord(textState.getText(), caretPosition);
                    StringBuilder builder = new StringBuilder(textState.getText());
                    builder.delete(start, end);
                    textState.setText(builder.toString());
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    StringBuilder builder = new StringBuilder(textState.getText());
                    builder.deleteCharAt(caretPosition);
                    textState.setText(builder.toString());
                    gui.setCaretPosition(caretPosition);
                    gui.setStartSelectionIndex(caretPosition);
                    gui.setEndSelectionIndex(caretPosition);
                }
            } else {
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }
        }
    }

    /**
     * Backspace action. Deletes selected text or symbol before caret or words before caret.
     *
     * @param gui  gui to remove text data.
     * @param mods key mods.
     */
    private void keyBackSpaceAction(TextAreaField gui, int mods) {
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
                if (isControlPressed(mods)) {
                    start = findPrevWord(textState.getText(), caretPosition);
                    StringBuilder builder = new StringBuilder(textState.getText());
                    builder.delete(start, end);
                    textState.setText(builder.toString());
                    gui.setCaretPosition(start);
                    gui.setStartSelectionIndex(start);
                    gui.setEndSelectionIndex(start);
                } else {
                    int newCaretPosition = caretPosition - 1;
                    StringBuilder builder = new StringBuilder(textState.getText());
                    builder.deleteCharAt(newCaretPosition);
                    textState.setText(builder.toString());
                    gui.setCaretPosition(newCaretPosition);
                    gui.setStartSelectionIndex(newCaretPosition);
                    gui.setEndSelectionIndex(newCaretPosition);
                }
            } else {
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
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
    private void keyEnterAction(TextAreaField gui) {
        if (gui.isEditable()) {
            int start = gui.getStartSelectionIndex();
            int end = gui.getEndSelectionIndex();
            TextState textState = gui.getTextState();
            if (start != end) {
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }

                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
                gui.setCaretPosition(start);
                gui.setStartSelectionIndex(start);
                gui.setEndSelectionIndex(start);
            }

            int caretPosition = gui.getCaretPosition();
            StringBuilder builder = new StringBuilder(textState.getText());
            builder.insert(caretPosition, "\n");
            textState.setText(builder.toString());
            int newCaretPosition = caretPosition + 1;
            gui.setStartSelectionIndex(newCaretPosition);
            gui.setEndSelectionIndex(newCaretPosition);
            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyEndAction(TextAreaField gui, int mods) {
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

    private void keyHomeAction(TextAreaField gui, int mods) {
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

    private void keyDownAction(TextAreaField gui, int mods) {
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

    private void keyUpAction(TextAreaField gui, int mods) {
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

    private void keyRightAction(TextAreaField gui, int mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition + 1;
        // reset if out of bounds
        if (newCaretPosition >= textState.length()) {
            newCaretPosition = textState.length();
        }
        if (isControlPressed(mods)) {
            newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
        }
        gui.setEndSelectionIndex(newCaretPosition);
        if ((mods & GLFW_MOD_SHIFT) == 0) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyLeftAction(TextAreaField gui, int mods) {
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition - 1;
        // reset if out of bounds.
        if (newCaretPosition <= 0) {
            newCaretPosition = 0;
        }
        if (isControlPressed(mods)) {
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
        return obj != null && (obj == this || obj.getClass() == this.getClass());
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

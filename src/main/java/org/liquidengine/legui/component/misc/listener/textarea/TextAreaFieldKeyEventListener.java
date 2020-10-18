package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldContentChangeEvent;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

import java.util.Set;

import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextAreaFieldKeyEventListener implements EventListener<KeyboardEvent> {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyboardEvent event) {
        KeyCode keyCode = event.getKey().getKeyCode();
        if (keyCode == KeyCode.LEFT_SHIFT
            || keyCode == KeyCode.RIGHT_SHIFT
            || keyCode == KeyCode.LEFT_CONTROL
            || keyCode == KeyCode.RIGHT_CONTROL
            || keyCode == KeyCode.LEFT_ALT
            || keyCode == KeyCode.RIGHT_ALT)
            return;

        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        boolean pressed = event.getAction() != KeyAction.RELEASE;

        String oldText = textAreaField.getTextState().getText();

        if (!pressed) {
            EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
            return;
        }

        processKey(textAreaField, event);
        String newText = textAreaField.getTextState().getText();
        if (!oldText.equals(newText)) {
            EventProcessorProvider.getInstance().pushEvent(
                new TextAreaFieldContentChangeEvent<>(
                    (TextAreaField) event.getTargetComponent(),
                    event.getContext(), event.getFrame(), oldText, newText));
        }
        EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
    }

    private void processKey(TextAreaField textAreaField, KeyboardEvent<?> event) {
        KeyCode key = event.getKey().getKeyCode();
        Set<KeyMod> mods = event.getMods();
        if (key == KeyCode.LEFT) {
            keyLeftAction(textAreaField, mods);
        } else if (key == KeyCode.RIGHT) {
            keyRightAction(textAreaField, mods);
        } else if (key == KeyCode.UP) {
            keyUpAction(textAreaField, mods);
        } else if (key == KeyCode.DOWN) {
            keyDownAction(textAreaField, mods);
        } else if (key == KeyCode.HOME) {
            keyHomeAction(textAreaField, mods);
        } else if (key == KeyCode.END) {
            keyEndAction(textAreaField, mods);
        } else if (key == KeyCode.ENTER || key == KeyCode.NUMPAD_ENTER) {
            keyEnterAction(textAreaField);
        } else if (key == KeyCode.BACKSPACE) {
            keyBackSpaceAction(textAreaField, mods);
        } else if (key == KeyCode.DELETE) {
            keyDeleteAction(textAreaField, mods);
        } else if (key == KeyCode.TAB) {
            addTab(textAreaField, mods);
        }
    }

    /**
     * Used to insert '\t' symbol.
     *
     * @param textAreaField text area to work with.
     * @param mods          key mods.
     */
    private void addTab(TextAreaField textAreaField, Set<KeyMod> mods) {
        if (mods.contains(KeyMod.CONTROL)) {
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
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui  gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextAreaField gui, Set<KeyMod> mods) {
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
                if (mods.contains(KeyMod.CONTROL)) {
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
    private void keyBackSpaceAction(TextAreaField gui, Set<KeyMod> mods) {
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
                if (mods.contains(KeyMod.CONTROL)) {
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
            StringBuilder builder = new StringBuilder(textState.getText());
            int caretPosition = gui.getCaretPosition();

            if (start != end) {
                if (start > end) {
                    start = gui.getEndSelectionIndex();
                    end = gui.getStartSelectionIndex();
                }

                builder.delete(start, end);
                textState.setText(builder.toString());
                caretPosition = start;
            }

            builder.insert(caretPosition, "\n");
            textState.setText(builder.toString());

            int newCaretPosition = caretPosition + 1;

            gui.setStartSelectionIndex(newCaretPosition);
            gui.setEndSelectionIndex(newCaretPosition);
            gui.setCaretPosition(newCaretPosition);
        }
    }

    private void keyEndAction(TextAreaField gui, Set<KeyMod> mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();

        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData currentLine = getStartLineIndexAndLineNumber(lines, caretPosition);
        int cl = lines[currentLine.lineIndex].length();
        int delta = cl - currentLine.caretPositionInLine;

        int newCaretPosition;
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = text.length();
        } else {
            newCaretPosition = caretPosition + delta;
        }

        updateIndices(gui, mods, newCaretPosition);
    }

    private void keyHomeAction(TextAreaField gui, Set<KeyMod> mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        String text = textState.getText();
        String[] lines = text.split("\n", -1);
        LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);

        int newCaretPosition;
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = 0;
        } else {
            newCaretPosition = caretPosition - some.caretPositionInLine;
        }

        updateIndices(gui, mods, newCaretPosition);
    }

    private void keyDownAction(TextAreaField gui, Set<KeyMod> mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        if (caretPosition < textState.length()) {
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);

            int newCaretPosition;
            if (some.lineIndex < lines.length - 1) {
                int nl = lines[some.lineIndex + 1].length() + 1;
                int cl = lines[some.lineIndex].length() + 1;
                if (some.caretPositionInLine >= nl - 1) {
                    newCaretPosition = caretPosition + nl + cl - 1 - some.caretPositionInLine;
                } else {
                    newCaretPosition = caretPosition + cl;
                }
            } else {
                newCaretPosition = text.length();
            }

            updateIndices(gui, mods, newCaretPosition);
        }
    }

    private void keyUpAction(TextAreaField gui, Set<KeyMod> mods) {
        int caretPosition = gui.getCaretPosition();
        if (caretPosition > 0) {
            TextState textState = gui.getTextState();
            String text = textState.getText();
            String[] lines = text.split("\n", -1);
            LineData some = getStartLineIndexAndLineNumber(lines, caretPosition);

            int newCaretPosition;
            if (some.lineIndex > 0) {
                int nl = lines[some.lineIndex - 1].length() + 1;
                newCaretPosition = caretPosition - (some.caretPositionInLine >= nl - 1 ? some.caretPositionInLine + 1 : nl);
            } else {
                newCaretPosition = 0;
            }

            updateIndices(gui, mods, newCaretPosition);
        }
    }

    private void keyRightAction(TextAreaField gui, Set<KeyMod> mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();

        int newCaretPosition;
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
        } else {
            newCaretPosition = caretPosition + 1;
            // reset if out of bounds
            if (newCaretPosition >= textState.length()) {
                newCaretPosition = textState.length();
            }
        }

        updateIndices(gui, mods, newCaretPosition);
    }

    private void keyLeftAction(TextAreaField gui, Set<KeyMod> mods) {
        int caretPosition = gui.getCaretPosition();

        int newCaretPosition;
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
        } else {
            newCaretPosition = caretPosition - 1;
            // reset if out of bounds.
            if (newCaretPosition <= 0) {
                newCaretPosition = 0;
            }
        }

        updateIndices(gui, mods, newCaretPosition);
    }

    private void updateIndices(TextAreaField gui, Set<KeyMod> mods, int newCaretPosition) {
        if (!mods.contains(KeyMod.SHIFT)) {
            gui.setStartSelectionIndex(newCaretPosition);
        }

        gui.setEndSelectionIndex(newCaretPosition);
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

    private static class LineData {

        private final int caretPositionInLine;
        private final int lineIndex;

        private LineData(int caretPositionInLine, int lineIndex) {
            this.caretPositionInLine = caretPositionInLine;
            this.lineIndex = lineIndex;
        }
    }
}

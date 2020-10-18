package org.liquidengine.legui.component.misc.listener.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.event.textinput.TextInputContentChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.KeyboardEvent;
import org.liquidengine.legui.input.KeyAction;
import org.liquidengine.legui.input.KeyCode;
import org.liquidengine.legui.input.KeyMod;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

import java.util.Set;

import static org.liquidengine.legui.input.KeyCode.*;
import static org.liquidengine.legui.util.TextUtil.findNextWord;
import static org.liquidengine.legui.util.TextUtil.findPrevWord;

/**
 * Key event listener. Used to provide some text operations by keyboard.
 */
public class TextInputKeyEventListener implements EventListener<KeyboardEvent> {

    /**
     * Used to handle {@link KeyEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(KeyboardEvent event) {
        TextInput gui = (TextInput) event.getTargetComponent();
        boolean pressed = event.getAction() != KeyAction.RELEASE;
        if (!pressed) {
            return;
        }

        String oldText = gui.getTextState().getText();
        processKeys(event, gui);
        String newText = gui.getTextState().getText();
        if (!oldText.equals(newText)) {
            EventProcessorProvider.getInstance().pushEvent(
                new TextInputContentChangeEvent<>(gui, event.getContext(), event.getFrame(), oldText, newText));
        }
    }

    private void processKeys(KeyboardEvent<?> event, TextInput gui) {

        Set<KeyMod> mods = event.getMods();
        KeyCode key = event.getKey().getKeyCode();
        if (key == LEFT) {

            keyLeftAction(gui, mods);
        } else if (key == RIGHT) {
            keyRightAction(gui, mods);
        } else if (key == UP || key == HOME) {
            keyUpAndHomeAction(gui, mods);
        } else if ((key == DOWN || key == END)) {
            keyDownAndEndAction(gui, mods);
        } else if (key == BACKSPACE) {
            keyBackSpaceAction(gui, mods);
        } else if (key == DELETE) {
            keyDeleteAction(gui, mods);
        }
    }

    /**
     * Delete action. Used to delete selected text or symbol after caret or word after caret.
     *
     * @param gui  gui to remove data from text state.
     * @param mods key mods.
     */
    private void keyDeleteAction(TextInput gui, Set<KeyMod> mods) {
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
    private void keyBackSpaceAction(TextInput gui, Set<KeyMod> mods) {
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

    private void keyDownAndEndAction(TextInput gui, Set<KeyMod> mods) {
        int newCaretPosition = gui.getTextState().length();
        gui.setEndSelectionIndex(newCaretPosition);
        if (!mods.contains(KeyMod.SHIFT)) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);

    }

    private void keyUpAndHomeAction(TextInput gui, Set<KeyMod> mods) {
        int newCaretPosition = 0;
        gui.setEndSelectionIndex(newCaretPosition);
        if (!mods.contains(KeyMod.SHIFT)) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }

    private void keyRightAction(TextInput gui, Set<KeyMod> mods) {
        TextState textState = gui.getTextState();
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition + 1;
        // reset if out of bounds
        if (newCaretPosition >= textState.length()) {
            newCaretPosition = textState.length();
        }
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = findNextWord(gui.getTextState().getText(), caretPosition);
        }

        gui.setEndSelectionIndex(newCaretPosition);

        if (!mods.contains(KeyMod.SHIFT)) {
            gui.setStartSelectionIndex(newCaretPosition);
        }

        gui.setCaretPosition(newCaretPosition);
    }

    private void keyLeftAction(TextInput gui, Set<KeyMod> mods) {
        int caretPosition = gui.getCaretPosition();
        int newCaretPosition = caretPosition - 1;
        // reset if out of bounds.
        if (newCaretPosition <= 0) {
            newCaretPosition = 0;
        }
        if (mods.contains(KeyMod.CONTROL)) {
            newCaretPosition = findPrevWord(gui.getTextState().getText(), caretPosition);
        }
        gui.setEndSelectionIndex(newCaretPosition);
        if (!mods.contains(KeyMod.SHIFT)) {
            gui.setStartSelectionIndex(newCaretPosition);
        }
        gui.setCaretPosition(newCaretPosition);
    }
}

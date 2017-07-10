package org.liquidengine.legui.component.misc.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.listener.CharEventListener;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Char event listener for text area. Used to fill text area with symbols entered via keyboard.
 */
public class TextAreaCharEventListener implements CharEventListener {

    /**
     * Used to handle {@link CharEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(CharEvent event) {
        TextArea textArea = (TextArea) event.getComponent();
        if (textArea.isFocused() && textArea.isEditable() && !MOUSE_BUTTON_LEFT.isPressed()) {
            String str = cpToStr(event.getCodepoint());
            TextState textState = textArea.getTextState();
            int start = textArea.getStartSelectionIndex();
            int end = textArea.getEndSelectionIndex();
            if (start > end) {
                start = textArea.getEndSelectionIndex();
                end = textArea.getStartSelectionIndex();
            }
            if (start != end) {
                textState.delete(start, end);
                textArea.setCaretPosition(start);
                textArea.setStartSelectionIndex(start);
                textArea.setEndSelectionIndex(start);
            }
            int caretPosition = textArea.getCaretPosition();
            textState.insert(caretPosition, str);
            int newCaretPosition = caretPosition + str.length();
            textArea.setCaretPosition(newCaretPosition);
            textArea.setEndSelectionIndex(newCaretPosition);
            textArea.setStartSelectionIndex(newCaretPosition);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

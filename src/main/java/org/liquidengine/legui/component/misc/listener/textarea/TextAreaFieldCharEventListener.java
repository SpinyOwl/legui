package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.listener.CharEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Char event listener for text area. Used to fill text area with symbols entered via keyboard.
 */
public class TextAreaFieldCharEventListener implements CharEventListener {

    /**
     * Used to handle {@link CharEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(CharEvent event) {
        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        if (textAreaField.isFocused() && textAreaField.isEditable() && !MOUSE_BUTTON_LEFT.isPressed()) {
            String str = cpToStr(event.getCodepoint());
            TextState textState = textAreaField.getTextState();
            int start = textAreaField.getStartSelectionIndex();
            int end = textAreaField.getEndSelectionIndex();
            if (start > end) {
                start = textAreaField.getEndSelectionIndex();
                end = textAreaField.getStartSelectionIndex();
            }
            if (start != end) {
                StringBuilder builder = new StringBuilder(textState.getText());
                builder.delete(start, end);
                textState.setText(builder.toString());
                textAreaField.setCaretPosition(start);
                textAreaField.setStartSelectionIndex(start);
                textAreaField.setEndSelectionIndex(start);
            }
            int caretPosition = textAreaField.getCaretPosition();
            StringBuilder builder = new StringBuilder(textState.getText());
            builder.insert(caretPosition, str);
            textState.setText(builder.toString());
            int newCaretPosition = caretPosition + str.length();
            textAreaField.setCaretPosition(newCaretPosition);
            textAreaField.setEndSelectionIndex(newCaretPosition);
            textAreaField.setStartSelectionIndex(newCaretPosition);

            EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
        }
    }
}

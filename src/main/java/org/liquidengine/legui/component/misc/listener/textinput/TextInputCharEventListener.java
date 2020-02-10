package org.liquidengine.legui.component.misc.listener.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.event.textinput.TextInputContentChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.listener.CharEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;
import static org.liquidengine.legui.util.TextUtil.cpToStr;

/**
 * Char event listener for text input. Used to fill text area with symbols entered via keyboard.
 */
public class TextInputCharEventListener implements CharEventListener {

    /**
     * Used to handle {@link CharEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(CharEvent event) {
        TextInput textInput = (TextInput) event.getTargetComponent();
        if (textInput.isFocused() && textInput.isEditable() && !MOUSE_BUTTON_LEFT.isPressed()) {
            String str = cpToStr(event.getCodepoint());
            TextState textState = textInput.getTextState();
            String oldText = textState.getText();
            int start = textInput.getStartSelectionIndex();
            int end = textInput.getEndSelectionIndex();
            if (start > end) {
                start = textInput.getEndSelectionIndex();
                end = textInput.getStartSelectionIndex();
            }
            if (start != end) {
                StringBuilder t = new StringBuilder(textState.getText());
                t.delete(start, end);
                textState.setText(t.toString());
                textInput.setCaretPosition(start);
                textInput.setStartSelectionIndex(start);
                textInput.setEndSelectionIndex(start);
            }
            int caretPosition = textInput.getCaretPosition();
            StringBuilder t = new StringBuilder(textState.getText());
            t.insert(caretPosition, str);
            textState.setText(t.toString());
            int newCaretPosition = caretPosition + str.length();
            textInput.setCaretPosition(newCaretPosition);
            textInput.setEndSelectionIndex(newCaretPosition);
            textInput.setStartSelectionIndex(newCaretPosition);
            String newText = textState.getText();
            EventProcessorProvider.getInstance().pushEvent(new TextInputContentChangeEvent(textInput, event.getContext(), event.getFrame(), oldText, newText));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}

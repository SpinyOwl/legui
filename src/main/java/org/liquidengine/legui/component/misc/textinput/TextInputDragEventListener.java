package org.liquidengine.legui.component.misc.textinput;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

/**
 * Mouse drag event listener for text input. Used to update selection indices.
 */
public class TextInputDragEventListener implements MouseDragEventListener {

    @Override
    public void process(MouseDragEvent event) {
        TextInput textInput = (TextInput) event.getComponent();
        if (MOUSE_BUTTON_LEFT.isPressed()) {
            int mouseCaretPosition = textInput.getMouseCaretPosition();
            textInput.setCaretPosition(mouseCaretPosition);
            textInput.setEndSelectionIndex(mouseCaretPosition);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

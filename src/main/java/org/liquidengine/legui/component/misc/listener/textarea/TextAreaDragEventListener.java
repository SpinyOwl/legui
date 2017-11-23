package org.liquidengine.legui.component.misc.listener.textarea;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;

/**
 * Mouse drag event listener for text area. Used to update selection indices.
 */
public class TextAreaDragEventListener implements MouseDragEventListener {

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseDragEvent event) {
        TextArea textArea = (TextArea) event.getTargetComponent();
        if (MOUSE_BUTTON_LEFT.isPressed()) {
            int mouseCaretPosition = textArea.getMouseCaretPosition();
            textArea.setCaretPosition(mouseCaretPosition);
            textArea.setEndSelectionIndex(mouseCaretPosition);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

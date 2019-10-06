package org.liquidengine.legui.component.misc.listener.textarea;

import static org.liquidengine.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.listener.MouseDragEventListener;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;

/**
 * Mouse drag event listener for text area. Used to update selection indices.
 */
public class TextAreaFieldDragEventListener implements MouseDragEventListener {

    /**
     * Used to handle {@link MouseDragEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseDragEvent event) {
        TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
        if (MOUSE_BUTTON_LEFT.isPressed()) {
            int mouseCaretPosition = textAreaField.getMouseCaretPosition();
            textAreaField.setCaretPosition(mouseCaretPosition);
            textAreaField.setEndSelectionIndex(mouseCaretPosition);

            EventProcessorProvider.getInstance().pushEvent(new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass() == this.getClass());
    }
}

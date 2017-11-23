package org.liquidengine.legui.component.misc.listener.textarea;

import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.MouseClickEventListener;

/**
 * Mouse click event listener for text area. Used to update caret position.
 */
public class TextAreaMouseClickEventListener implements MouseClickEventListener {

    /**
     * Used to handle {@link MouseClickEvent}.
     *
     * @param event event to handle.
     */
    @Override
    public void process(MouseClickEvent event) {
        TextArea textArea = (TextArea) event.getTargetComponent();
        if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
            int mouseCaretPosition = textArea.getMouseCaretPosition();
            textArea.setCaretPosition(mouseCaretPosition);
            textArea.setStartSelectionIndex(mouseCaretPosition);
            textArea.setEndSelectionIndex(mouseCaretPosition);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && ((obj == this) || ((obj != this) && (obj.getClass() == this.getClass())));
    }
}

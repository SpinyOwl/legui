package com.spinyowl.legui.component.misc.listener.textarea;

import static com.spinyowl.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.listener.MouseDragEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

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

      EventProcessorProvider.getInstance().pushEvent(
          new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
    }
  }
}

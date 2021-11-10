package com.spinyowl.legui.component.misc.listener.textarea;

import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.event.textarea.TextAreaFieldUpdateEvent;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.event.MouseClickEvent.MouseClickAction;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;

/**
 * Mouse click event listener for text area. Used to update caret position.
 */
public class TextAreaFieldMouseClickEventListener implements MouseClickEventListener {

  /**
   * Used to handle {@link MouseClickEvent}.
   *
   * @param event event to handle.
   */
  @Override
  public void process(MouseClickEvent event) {
    TextAreaField textAreaField = (TextAreaField) event.getTargetComponent();
    if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
      int mouseCaretPosition = textAreaField.getMouseCaretPosition();
      textAreaField.setCaretPosition(mouseCaretPosition);
      textAreaField.setEndSelectionIndex(mouseCaretPosition);
      if (!event.isModShift()) {
        textAreaField.setStartSelectionIndex(mouseCaretPosition);
      }
    }
    if (event.getAction() == MouseClickAction.RELEASE) {
      EventProcessorProvider.getInstance().pushEvent(
          new TextAreaFieldUpdateEvent(textAreaField, event.getContext(), event.getFrame()));
    }
  }
}

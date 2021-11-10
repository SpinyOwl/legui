package com.spinyowl.legui.component.misc.listener.textinput;

import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;

/**
 * Mouse click event listener for text input. Used to update caret position.
 */
public class TextInputMouseClickEventListener implements MouseClickEventListener {

  /**
   * Used to handle {@link MouseClickEvent}.
   *
   * @param event event to handle.
   */
  @Override
  public void process(MouseClickEvent event) {
    TextInput gui = (TextInput) event.getTargetComponent();
    int mouseCaretPosition = gui.getMouseCaretPosition();
    if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
      gui.setCaretPosition(mouseCaretPosition);
      gui.setEndSelectionIndex(mouseCaretPosition);
      if (!event.isModShift()) {
        gui.setStartSelectionIndex(mouseCaretPosition);
      }
    }
  }
}

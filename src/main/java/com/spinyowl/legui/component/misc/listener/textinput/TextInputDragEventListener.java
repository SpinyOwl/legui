package com.spinyowl.legui.component.misc.listener.textinput;

import static com.spinyowl.legui.input.Mouse.MouseButton.MOUSE_BUTTON_LEFT;

import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.event.MouseDragEvent;
import com.spinyowl.legui.listener.MouseDragEventListener;

/**
 * Mouse drag event listener for text input. Used to update selection indices.
 */
public class TextInputDragEventListener implements MouseDragEventListener {

  @Override
  public void process(MouseDragEvent event) {
    TextInput textInput = (TextInput) event.getTargetComponent();
    if (MOUSE_BUTTON_LEFT.isPressed()) {
      int mouseCaretPosition = textInput.getMouseCaretPosition();
      textInput.setCaretPosition(mouseCaretPosition);
      textInput.setEndSelectionIndex(mouseCaretPosition);
    }
  }
}

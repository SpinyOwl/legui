package com.spinyowl.legui.component.misc.listener.button;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.event.button.ButtonContentChangeEvent;
import com.spinyowl.legui.component.event.button.ButtonWidthChangeEvent;
import com.spinyowl.legui.listener.EventListener;

public class UpdateButtonStyleWidthListener implements EventListener<ButtonWidthChangeEvent> {

  /**
   * Used to handle {@link ButtonContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  public void process(ButtonWidthChangeEvent event) {
    Button button = event.getTargetComponent();
    float textWidth = button.getTextState().getTextWidth();
    button.getStyle().setWidth(textWidth);
  }

}

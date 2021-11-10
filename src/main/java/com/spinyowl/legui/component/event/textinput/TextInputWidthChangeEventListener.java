package com.spinyowl.legui.component.event.textinput;

import com.spinyowl.legui.listener.EventListener;


public interface TextInputWidthChangeEventListener extends
    EventListener<TextInputWidthChangeEvent> {

  /**
   * Used to handle {@link TextInputWidthChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextInputWidthChangeEvent event);
}

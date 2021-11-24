package com.spinyowl.legui.component.event.button;

import com.spinyowl.legui.listener.EventListener;


public interface ButtonWidthChangeEventListener extends EventListener<ButtonWidthChangeEvent> {

  /**
   * Used to handle {@link ButtonWidthChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(ButtonWidthChangeEvent event);
}

package com.spinyowl.legui.component.event.button;

import com.spinyowl.legui.listener.EventListener;


public interface ButtonContentChangeEventListener extends EventListener<ButtonContentChangeEvent> {

  /**
   * Used to handle {@link ButtonContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(ButtonContentChangeEvent event);
}

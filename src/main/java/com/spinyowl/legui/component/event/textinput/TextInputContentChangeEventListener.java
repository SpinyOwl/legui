package com.spinyowl.legui.component.event.textinput;

import com.spinyowl.legui.listener.EventListener;


public interface TextInputContentChangeEventListener extends
    EventListener<TextInputContentChangeEvent> {

  /**
   * Used to handle {@link TextInputContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextInputContentChangeEvent event);
}

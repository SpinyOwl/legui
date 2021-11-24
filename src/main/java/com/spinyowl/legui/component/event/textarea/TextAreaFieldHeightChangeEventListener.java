package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.listener.EventListener;


public interface TextAreaFieldHeightChangeEventListener extends
    EventListener<TextAreaFieldHeightChangeEvent> {

  /**
   * Used to handle {@link TextAreaFieldHeightChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextAreaFieldHeightChangeEvent event);
}

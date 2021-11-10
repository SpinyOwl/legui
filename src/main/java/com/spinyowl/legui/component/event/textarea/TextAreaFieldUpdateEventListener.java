package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.listener.EventListener;


public interface TextAreaFieldUpdateEventListener extends EventListener<TextAreaFieldUpdateEvent> {

  /**
   * Used to handle {@link TextAreaFieldUpdateEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextAreaFieldUpdateEvent event);
}

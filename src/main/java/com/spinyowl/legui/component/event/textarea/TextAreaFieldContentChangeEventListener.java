package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.listener.EventListener;


public interface TextAreaFieldContentChangeEventListener extends
    EventListener<TextAreaFieldContentChangeEvent> {

  /**
   * Used to handle {@link TextAreaFieldContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextAreaFieldContentChangeEvent event);
}

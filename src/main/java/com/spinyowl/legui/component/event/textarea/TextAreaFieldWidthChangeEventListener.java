package com.spinyowl.legui.component.event.textarea;

import com.spinyowl.legui.listener.EventListener;


public interface TextAreaFieldWidthChangeEventListener extends
    EventListener<TextAreaFieldWidthChangeEvent> {

  /**
   * Used to handle {@link TextAreaFieldWidthChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TextAreaFieldWidthChangeEvent event);
}

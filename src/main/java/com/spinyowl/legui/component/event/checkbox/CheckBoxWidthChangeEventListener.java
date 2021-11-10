package com.spinyowl.legui.component.event.checkbox;

import com.spinyowl.legui.listener.EventListener;


public interface CheckBoxWidthChangeEventListener extends EventListener<CheckBoxWidthChangeEvent> {

  /**
   * Used to handle {@link CheckBoxWidthChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(CheckBoxWidthChangeEvent event);
}

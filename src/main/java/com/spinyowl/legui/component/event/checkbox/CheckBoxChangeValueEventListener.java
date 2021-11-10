package com.spinyowl.legui.component.event.checkbox;

import com.spinyowl.legui.listener.EventListener;


public interface CheckBoxChangeValueEventListener extends EventListener<CheckBoxChangeValueEvent> {

  /**
   * Used to handle {@link CheckBoxChangeValueEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(CheckBoxChangeValueEvent event);
}

package com.spinyowl.legui.component.event.label;

import com.spinyowl.legui.listener.EventListener;


public interface LabelContentChangeEventListener extends EventListener<LabelContentChangeEvent> {

  /**
   * Used to handle {@link LabelContentChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(LabelContentChangeEvent event);
}

package com.spinyowl.legui.component.event.label;

import com.spinyowl.legui.listener.EventListener;


public interface LabelWidthChangeEventListener extends EventListener<LabelWidthChangeEvent> {

  /**
   * Used to handle {@link LabelWidthChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(LabelWidthChangeEvent event);
}

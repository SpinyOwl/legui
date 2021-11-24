package com.spinyowl.legui.component.event.tooltip;

import com.spinyowl.legui.listener.EventListener;


public interface TooltipTextSizeChangeEventListener extends
    EventListener<TooltipTextSizeChangeEvent> {

  /**
   * Used to handle {@link TooltipTextSizeChangeEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(TooltipTextSizeChangeEvent event);
}

package com.spinyowl.legui.component.event.scrollbar;

import com.spinyowl.legui.listener.EventListener;

/**
 * Defines contract for listener of {@link ScrollBarChangeValueEvent} event.
 */
public interface ScrollBarChangeValueEventListener extends
    EventListener<ScrollBarChangeValueEvent> {

  /**
   * Used to handle {@link ScrollBarChangeValueEvent} event.
   *
   * @param event event to handle.
   */
  @Override
  void process(ScrollBarChangeValueEvent event);
}

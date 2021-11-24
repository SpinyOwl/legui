package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.CharEvent;


public interface CharEventListener extends EventListener<CharEvent> {

  /**
   * Used to handle {@link CharEvent}.
   *
   * @param event event to handle.
   */
  void process(CharEvent event);
}

package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.MouseDragEvent;


public interface MouseDragEventListener extends EventListener<MouseDragEvent> {

  /**
   * Used to handle {@link MouseDragEvent}.
   *
   * @param event event to handle.
   */
  @Override
  void process(MouseDragEvent event);
}

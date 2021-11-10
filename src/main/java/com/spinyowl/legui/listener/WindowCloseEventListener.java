package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.WindowCloseEvent;

/**
 * The listener interface that used to handle {@link WindowCloseEvent}.
 */
public interface WindowCloseEventListener extends EventListener<WindowCloseEvent> {

  /**
   * Used to handle {@link WindowCloseEvent}.
   *
   * @param event event to handle.
   */
  void process(WindowCloseEvent event);
}

package com.spinyowl.legui.component.event.widget;

import com.spinyowl.legui.listener.EventListener;


public interface WidgetCloseEventListener<T extends WidgetCloseEvent> extends EventListener<T> {

  /**
   * Used to handle {@link WidgetCloseEvent} event.
   *
   * @param event event to handle.
   */
  void process(WidgetCloseEvent event);
}

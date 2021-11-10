package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.WindowFocusEvent;


public interface WindowFocusEventListener extends EventListener<WindowFocusEvent> {

  void process(WindowFocusEvent event);
}

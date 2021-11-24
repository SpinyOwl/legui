package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.WindowPosEvent;


public interface WindowPosEventListener extends EventListener<WindowPosEvent> {

  void process(WindowPosEvent event);
}

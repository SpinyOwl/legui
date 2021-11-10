package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.WindowRefreshEvent;


public interface WindowRefreshEventListener extends EventListener<WindowRefreshEvent> {

  void process(WindowRefreshEvent event);
}

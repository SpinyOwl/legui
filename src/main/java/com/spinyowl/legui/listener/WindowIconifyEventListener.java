package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.WindowIconifyEvent;


public interface WindowIconifyEventListener extends EventListener<WindowIconifyEvent> {

  void process(WindowIconifyEvent event);
}

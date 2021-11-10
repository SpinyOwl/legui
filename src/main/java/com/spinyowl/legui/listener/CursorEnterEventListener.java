package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.CursorEnterEvent;


public interface CursorEnterEventListener extends EventListener<CursorEnterEvent> {

  void process(CursorEnterEvent event);
}

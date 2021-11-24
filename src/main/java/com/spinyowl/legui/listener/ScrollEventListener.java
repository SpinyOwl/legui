package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.ScrollEvent;


public interface ScrollEventListener extends EventListener<ScrollEvent> {

  void process(ScrollEvent event);
}

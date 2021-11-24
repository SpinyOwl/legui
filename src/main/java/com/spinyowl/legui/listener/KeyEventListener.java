package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.KeyEvent;


public interface KeyEventListener extends EventListener<KeyEvent> {

  void process(KeyEvent event);
}

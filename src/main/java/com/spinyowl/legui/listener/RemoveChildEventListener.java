package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.RemoveChildEvent;

public interface RemoveChildEventListener extends EventListener<RemoveChildEvent> {

  @Override
  void process(RemoveChildEvent event);

}

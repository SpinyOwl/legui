package com.spinyowl.legui.listener;

import com.spinyowl.legui.event.AddChildEvent;

public interface AddChildEventListener extends EventListener<AddChildEvent> {

  @Override
  void process(AddChildEvent event);

}

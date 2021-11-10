package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.event.CharEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemCharEvent;


public class CharEventHandler implements SystemEventHandler<SystemCharEvent> {

  @Override
  public void handle(SystemCharEvent event, Frame frame, Context context) {
    Component focusedGui = context.getFocusedGui();
    if (focusedGui == null) {
      return;
    }

    EventProcessorProvider.getInstance()
        .pushEvent(new CharEvent(focusedGui, context, frame, event.codepoint));
  }
}

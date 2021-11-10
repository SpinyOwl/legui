package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.DropEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemDropEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DropEventHandler extends AbstractSystemEventHandler<SystemDropEvent> {

  @Override
  protected boolean handle(SystemDropEvent event, Layer layer, Context context, Frame frame) {
    List<Component> targetComponentList = SehUtil.getTargetComponentList(layer,
        Mouse.getCursorPosition());
    List<String> strings = Arrays.stream(event.strings).collect(Collectors.toList());
    for (Component component : targetComponentList) {
      EventProcessorProvider.getInstance()
          .pushEvent(new DropEvent<>(component, context, frame, strings));
    }
    return false;
  }
}

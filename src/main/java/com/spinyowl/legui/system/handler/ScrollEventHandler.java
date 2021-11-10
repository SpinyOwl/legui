package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.input.Mouse;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemScrollEvent;
import java.util.List;


public class ScrollEventHandler extends AbstractSystemEventHandler<SystemScrollEvent> {

  @Override
  protected boolean handle(SystemScrollEvent event, Layer layer, Context context, Frame frame) {
    List<Component> targetComponentList = SehUtil.getTargetComponentList(layer,
        Mouse.getCursorPosition());
    for (Component component : targetComponentList) {
      EventProcessorProvider.getInstance()
          .pushEvent(new ScrollEvent<>(component, context, frame, event.xoffset, event.yoffset));
    }
    return false;
  }
}

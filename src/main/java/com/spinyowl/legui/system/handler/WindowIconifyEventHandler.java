package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowIconifyEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowIconifyEvent;
import java.util.List;


public class WindowIconifyEventHandler extends
    AbstractSystemEventHandler<SystemWindowIconifyEvent> {

  @Override
  protected boolean handle(SystemWindowIconifyEvent event, Layer layer, Context context,
      Frame frame) {
    pushEvent(layer, event, context, frame);
    return false;
  }


  private void pushEvent(Component component, SystemWindowIconifyEvent event, Context context,
      Frame frame) {
    if (!(component.isVisible())) {
      return;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new WindowIconifyEvent(component, context, frame, event.iconified));
    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, event, context, frame);
    }
  }
}

package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowCloseEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowCloseEvent;
import java.util.List;


public class WindowCloseEventHandler extends AbstractSystemEventHandler<SystemWindowCloseEvent> {

  @Override
  protected boolean handle(SystemWindowCloseEvent event, Layer layer, Context context,
      Frame frame) {
    pushEvent(layer, context, frame);
    return false;
  }

  private void pushEvent(Component component, Context context, Frame frame) {
    if (!(component.isVisible())) {
      return;
    }
    EventProcessorProvider.getInstance().pushEvent(new WindowCloseEvent(component, context, frame));
    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, context, frame);
    }
  }
}

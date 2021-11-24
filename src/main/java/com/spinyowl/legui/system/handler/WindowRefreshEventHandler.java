package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowRefreshEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowRefreshEvent;
import java.util.List;


public class WindowRefreshEventHandler extends
    AbstractSystemEventHandler<SystemWindowRefreshEvent> {

  @Override
  protected boolean handle(SystemWindowRefreshEvent event, Layer layer, Context context,
      Frame frame) {
    pushEvent(layer, context, frame);
    return false;
  }

  private void pushEvent(Component component, Context context, Frame frame) {
    if (!(component.isVisible())) {
      return;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new WindowRefreshEvent(component, context, frame));
    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, context, frame);
    }
  }
}

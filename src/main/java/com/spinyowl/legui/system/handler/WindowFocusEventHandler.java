package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowFocusEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowFocusEvent;
import java.util.List;


public class WindowFocusEventHandler extends AbstractSystemEventHandler<SystemWindowFocusEvent> {

  @Override
  protected boolean handle(SystemWindowFocusEvent event, Layer layer, Context context,
      Frame frame) {
    pushEvent(layer, event, context, frame);
    return false;
  }


  private void pushEvent(Component component, SystemWindowFocusEvent event, Context context,
      Frame frame) {
    if (!(component.isVisible())) {
      return;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new WindowFocusEvent(component, context, frame, event.focused));

    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, event, context, frame);
    }

  }
}

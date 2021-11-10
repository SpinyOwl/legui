package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.event.WindowSizeEvent;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.event.SystemWindowSizeEvent;
import java.util.Collections;
import java.util.List;


public class WindowSizeEventHandler implements SystemEventHandler<SystemWindowSizeEvent> {

  @Override
  public void handle(SystemWindowSizeEvent event, Frame frame, Context context) {
    List<Layer> layers = frame.getAllLayers();
    Collections.reverse(layers);
    for (Layer layer : layers) {
      if (!layer.isVisible() || !layer.isEnabled()) {
        continue;
      }
      pushEvent(layer, event, context, frame);
    }
  }

  private void pushEvent(Component component, SystemWindowSizeEvent event, Context context,
      Frame frame) {
    if (!component.isVisible() || !component.isEnabled()) {
      return;
    }
    EventProcessorProvider.getInstance()
        .pushEvent(new WindowSizeEvent(component, context, frame, event.width, event.height));
    List<Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      pushEvent(child, event, context, frame);
    }
  }
}

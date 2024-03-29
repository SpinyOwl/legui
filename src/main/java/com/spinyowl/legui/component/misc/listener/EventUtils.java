package com.spinyowl.legui.component.misc.listener;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.component.Viewport;
import com.spinyowl.legui.event.ScrollEvent;
import com.spinyowl.legui.system.handler.SehUtil;
import java.util.List;
import org.joml.Vector2f;

public final class EventUtils {

  private EventUtils() {
  }


  public static boolean hasViewportsInAboveLayersUnderCursor(Component component,
      Vector2f cursorPosition) {
    Frame frame = component.getFrame();
    List<Layer> allLayers = frame.getAllLayers();
    Layer currentLayer = component.getLayer();

    int currentLayerIndex = allLayers.indexOf(currentLayer);
    int maxIndex = allLayers.size() - 1;
    // if there are some layers above current layer with other viewport we have to skip processing this event
    if (currentLayerIndex < maxIndex) {
      for (int i = currentLayerIndex + 1; i <= maxIndex; i++) {
        List<Component> layerTargets = SehUtil.getTargetComponentList(allLayers.get(i),
            cursorPosition);
        if (layerTargets.stream().anyMatch(c -> c instanceof Viewport)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean hasScrollableInChildComponentsUnderCursor(Component targetComponent,
      Vector2f cursorPosition) {
    // if there is some viewport in current viewport we have to skip this event
    List<Component> targetList = SehUtil.getTargetComponentList(targetComponent, cursorPosition);
    for (Component component : targetList) {
      if (!component.getListenerMap().getListeners(ScrollEvent.class).isEmpty()
          && component != targetComponent) {
        return true;
      }
    }
    return false;
  }

}

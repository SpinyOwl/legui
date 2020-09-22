package org.liquidengine.legui.component.misc.listener;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.Viewport;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.system.handler.SehUtil;

import java.util.List;

public final class EventUtils {
    private EventUtils(){}


    public static boolean hasViewportsInAboveLayersUnderCursor(Component component, Vector2f cursorPosition) {
        Frame frame = component.getFrame();
        List<Layer> allLayers = frame.getAllLayers();
        Layer currentLayer = component.getLayer();

        int currentLayerIndex = allLayers.indexOf(currentLayer);
        int maxIndex = allLayers.size() - 1;
        // if there are some layers above current layer with other viewport we have to skip processing this event
        if (currentLayerIndex < maxIndex) {
            for (int i = currentLayerIndex + 1; i <= maxIndex; i++) {
                List<Component> layerTargets = SehUtil.getTargetComponentList(allLayers.get(i), cursorPosition);
                if (layerTargets.stream().anyMatch(c -> c instanceof Viewport)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasScrollableInChildComponentsUnderCursor(Component targetComponent, Vector2f cursorPosition) {
        // if there is some viewport in current viewport we have to skip this event
        List<Component> targetList = SehUtil.getTargetComponentList(targetComponent, cursorPosition);
        for (Component component : targetList) {
            if(!component.getListenerMap().getListeners(ScrollEvent.class).isEmpty() && component != targetComponent) {
                return true;
            }
        }
        return false;
    }

}

package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemScrollEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandler implements SystemEventHandler<SystemScrollEvent> {
    @Override
    public void process(SystemScrollEvent event, Frame frame, Context context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            // if not visible or not enabled we should continue to next layer.
            if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;

            // else proceed with event.
            Component intersectedComponent = SehUtil.getTargetComponent(layer, context.getCursorPosition());
            if (intersectedComponent != null) {
                context.getEventProcessor().pushEvent(new ScrollEvent(intersectedComponent, event.xoffset, event.yoffset));
                return;
            }
            if (!layer.isEventPassable()) return;
        }
    }
}

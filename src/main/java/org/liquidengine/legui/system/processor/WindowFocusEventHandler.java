package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.WindowFocusEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowFocusEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEventHandler implements SystemEventHandler<SystemWindowFocusEvent> {
    @Override
    public void process(SystemWindowFocusEvent event, Frame frame, Context context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            pushEvent(layer.getContainer(), event, context);
            if (!layer.isEventPassable()) return;
        }
    }

    private void pushEvent(Component component, SystemWindowFocusEvent event, Context context) {
        if (!(component instanceof Controller)) return;
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowFocusEvent((Controller) component, event.focused));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }

    }
}

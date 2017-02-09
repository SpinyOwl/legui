package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.WindowCloseEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowCloseEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowCloseEventHandler implements SystemEventHandler<SystemWindowCloseEvent> {
    @Override
    public void process(SystemWindowCloseEvent event, Frame frame, Context context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            pushEvent(layer.getContainer(), context);
            if (!layer.isEventPassable()) return;
        }
    }

    private void pushEvent(Component component, Context context) {
        if (!(component instanceof Controller)) return;
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowCloseEvent((Controller) component));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, context);
            }
        }
    }
}

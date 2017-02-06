package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowRefreshEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowRefreshEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowRefreshEventHandler implements SystemEventHandler<SystemWindowRefreshEvent> {
    @Override
    public void process(SystemWindowRefreshEvent event, Frame frame, Context context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            pushEvent(layer.getContainer(), context);
            if(!layer.isEventPassable()) return;
        }
    }

    private void pushEvent(Component component, Context context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowRefreshEvent(component));
        if (component instanceof ComponentContainer) {
            List<Component> childs = ((ComponentContainer) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, context);
            }
        }
    }
}

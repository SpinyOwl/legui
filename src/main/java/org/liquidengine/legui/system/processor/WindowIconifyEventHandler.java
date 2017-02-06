package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowIconifyEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEventHandler implements SystemEventHandler<SystemWindowIconifyEvent> {
    @Override
    public void process(SystemWindowIconifyEvent event, Frame frame, Context context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            pushEvent(layer.getContainer(), event, context);
            if(!layer.isEventPassable()) return;
        }
    }

    private void pushEvent(Component component, SystemWindowIconifyEvent event, Context context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowIconifyEvent(component, event.iconified));
        if(component instanceof ComponentContainer){
            List<Component> childs = ((ComponentContainer) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

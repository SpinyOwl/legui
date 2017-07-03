package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiWindowSizeEvent;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemWindowSizeEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowSizeEventHandlerLegui implements LeguiSystemEventHandler<LeguiSystemWindowSizeEvent> {
    @Override
    public void handle(LeguiSystemWindowSizeEvent event, Frame frame, LeguiContext context) {
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;
                pushEvent(layer.getContainer(), event, context);
            }
        }
    }

    private void pushEvent(Component component, LeguiSystemWindowSizeEvent event, LeguiContext context) {
        if (!component.isVisible() || !component.isEnabled()) return;
        context.getEventProcessor().pushEvent(new LeguiWindowSizeEvent(component, context, event.width, event.height));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

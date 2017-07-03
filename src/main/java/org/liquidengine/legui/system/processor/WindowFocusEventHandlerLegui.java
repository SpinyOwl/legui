package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiWindowFocusEvent;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemWindowFocusEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemWindowFocusEvent> {
    @Override
    protected boolean handle(LeguiSystemWindowFocusEvent event, Layer layer, LeguiContext context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }


    private void pushEvent(Component component, LeguiSystemWindowFocusEvent event, LeguiContext context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new LeguiWindowFocusEvent(component, context, event.focused));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }

    }
}

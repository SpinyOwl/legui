package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiWindowIconifyEvent;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemWindowIconifyEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemWindowIconifyEvent> {
    @Override
    protected boolean handle(LeguiSystemWindowIconifyEvent event, Layer layer, LeguiContext context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }


    private void pushEvent(Component component, LeguiSystemWindowIconifyEvent event, LeguiContext context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new LeguiWindowIconifyEvent(component, context, event.iconified));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

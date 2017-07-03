package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiEvent;
import org.liquidengine.legui.event.LeguiWindowPosEvent;
import org.liquidengine.legui.listener.LeguiEventProcessor;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemWindowPosEvent;

import java.util.List;

/**
 * This class instance used to handle {@link LeguiWindowPosEvent}.
 */
public class WindowPosEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemWindowPosEvent> {

    /**
     * This method used to handle {@link LeguiWindowPosEvent}.
     *
     * @param event   event to be processed.
     * @param layer   target event layer.
     * @param context context.
     * @return true if this event was handled and should not be handled more.
     */
    @Override
    protected boolean handle(LeguiSystemWindowPosEvent event, Layer layer, LeguiContext context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }

    /**
     * Used to push {@link LeguiWindowPosEvent} instance of {@link LeguiEvent} to {@link LeguiEventProcessor}.
     *
     * @param component component for which should be created {@link LeguiWindowPosEvent}
     * @param event     event to push.
     * @param context   context.
     */
    private void pushEvent(Component component, LeguiSystemWindowPosEvent event, LeguiContext context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new LeguiWindowPosEvent(component, context, event.xpos, event.ypos));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

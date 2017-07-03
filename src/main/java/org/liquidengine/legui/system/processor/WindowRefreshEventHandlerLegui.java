package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiWindowRefreshEvent;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemWindowRefreshEvent;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowRefreshEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemWindowRefreshEvent> {

    @Override
    protected boolean handle(LeguiSystemWindowRefreshEvent event, Layer layer, LeguiContext context) {
        pushEvent(layer.getContainer(), context);
        return false;
    }

    private void pushEvent(Component component, LeguiContext context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new LeguiWindowRefreshEvent(component, context));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, context);
            }
        }
    }
}

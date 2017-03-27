package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowFocusEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowFocusEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowFocusEventHandler extends AbstractSystemEventHandler<SystemWindowFocusEvent> {
    @Override
    protected boolean handle(SystemWindowFocusEvent event, Layer layer, Context context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }


    private void pushEvent(Component component, SystemWindowFocusEvent event, Context context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowFocusEvent(component, context, event.focused));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }

    }
}

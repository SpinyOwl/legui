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
public class WindowCloseEventHandler extends AbstractSystemEventHandler<SystemWindowCloseEvent> {
    @Override
    protected boolean process(SystemWindowCloseEvent event, Layer layer, Context context) {
        pushEvent(layer.getContainer(), context);
        return false;
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

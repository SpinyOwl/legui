package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.WindowPosEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;
import org.liquidengine.legui.system.event.SystemWindowPosEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowPosEventHandler extends AbstractSystemEventHandler<SystemWindowPosEvent> {

    @Override
    protected boolean process(SystemWindowPosEvent event, Layer layer, Context context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }

    private void pushEvent(Component component, SystemWindowPosEvent event, Context context) {
        if (!(component instanceof Controller)) return;
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowPosEvent((Controller) component, event.xpos, event.ypos));
        if(component instanceof Container){
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

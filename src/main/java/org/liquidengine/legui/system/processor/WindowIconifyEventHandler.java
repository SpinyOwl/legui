package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.WindowIconifyEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEventHandler extends AbstractSystemEventHandler<SystemWindowIconifyEvent> {
    @Override
    protected boolean process(SystemWindowIconifyEvent event, Layer layer, Context context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }


    private void pushEvent(Component component, SystemWindowIconifyEvent event, Context context) {
        if (!(component.isVisible())) return;
        context.getEventProcessor().pushEvent(new WindowIconifyEvent(component, context.getFrame(), event.iconified));
        if(component instanceof Container){
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

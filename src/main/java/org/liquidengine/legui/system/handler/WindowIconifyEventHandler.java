package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowIconifyEvent;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class WindowIconifyEventHandler extends AbstractSystemEventHandler<SystemWindowIconifyEvent> {

    @Override
    protected boolean handle(SystemWindowIconifyEvent event, Layer layer, Context context, Frame frame) {
        pushEvent(layer.getContainer(), event, context, frame);
        return false;
    }


    private void pushEvent(Component component, SystemWindowIconifyEvent event, Context context, Frame frame) {
        if (!(component.isVisible())) {
            return;
        }
        EventProcessorProvider.getInstance().pushEvent(new WindowIconifyEvent(component, context, frame, event.iconified));
        List<Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            pushEvent(child, event, context, frame);
        }
    }
}

package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowRefreshEvent;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowRefreshEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 2/2/2017.
 */
public class WindowRefreshEventHandler extends AbstractSystemEventHandler<SystemWindowRefreshEvent> {

    @Override
    protected boolean handle(SystemWindowRefreshEvent event, Layer layer, Context context, Frame frame) {
        pushEvent(layer, context, frame);
        return false;
    }

    private void pushEvent(Component component, Context context, Frame frame) {
        if (!(component.isVisible())) {
            return;
        }
        EventProcessorProvider.getInstance().pushEvent(new WindowRefreshEvent(component, context, frame));
        List<Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            pushEvent(child, context, frame);
        }
    }
}

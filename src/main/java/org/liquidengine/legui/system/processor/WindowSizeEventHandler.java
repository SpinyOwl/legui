package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowSizeEvent;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowSizeEventHandler extends AbstractSystemEventHandler<SystemWindowSizeEvent> {
    @Override
    protected boolean process(SystemWindowSizeEvent event, Layer layer, Context context) {
        pushEvent(layer.getContainer(), event, context);
        return false;
    }

    private void pushEvent(Component component, SystemWindowSizeEvent event, Context context) {
        if (!component.isVisible() || !component.isEnabled()) return;
        context.getEventProcessor().pushEvent(new WindowSizeEvent(component, event.width, event.height));
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                pushEvent(child, event, context);
            }
        }
    }
}

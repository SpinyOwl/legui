package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemScrollEvent;

import java.util.List;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandler extends AbstractSystemEventHandler<SystemScrollEvent> {
    @Override
    protected boolean handle(SystemScrollEvent event, Layer layer, Context context) {
        List<Component> targetComponentList = SehUtil.getTargetComponentList(layer, Mouse.getCursorPosition());
        for (Component component : targetComponentList) {
            context.getEventProcessor().pushEvent(new ScrollEvent<>(component, context, event.xoffset, event.yoffset));
        }
        return false;
    }
}

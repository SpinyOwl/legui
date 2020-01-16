package org.liquidengine.legui.system.handler;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandler extends AbstractSystemEventHandler<SystemScrollEvent> {

    @Override
    protected boolean handle(SystemScrollEvent event, Layer layer, Context context, Frame frame) {
        List<Component> targetComponentList = SehUtil.getTargetComponentList(layer, Mouse.getCursorPosition());
        for (Component component : targetComponentList) {
            EventProcessorProvider.getInstance().pushEvent(new ScrollEvent<>(component, context, frame, event.xoffset, event.yoffset));
        }
        return false;
    }
}

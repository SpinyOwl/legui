package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandler extends AbstractSystemEventHandler<SystemScrollEvent> {
    @Override
    protected boolean process(SystemScrollEvent event, Layer layer, Context context) {
        Controller intersectedComponent = SehUtil.getTargetController(layer, Mouse.getCursorPosition());
        if (intersectedComponent != null) {
            context.getEventProcessor().pushEvent(new ScrollEvent(intersectedComponent, event.xoffset, event.yoffset));
            return true;
        }
        return false;
    }
}

package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiScrollEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemScrollEvent;

/**
 * Created by ShchAlexander on 03.02.2017.
 */
public class ScrollEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemScrollEvent> {
    @Override
    protected boolean handle(LeguiSystemScrollEvent event, Layer layer, LeguiContext context) {
        Component intersectedComponent = LeguiSystemEventHandlerUtil.getTargetComponent(layer, Mouse.getCursorPosition());
        if (intersectedComponent != null) {
            context.getEventProcessor().pushEvent(new LeguiScrollEvent(intersectedComponent, context, event.xoffset, event.yoffset));
            return true;
        }
        return false;
    }
}

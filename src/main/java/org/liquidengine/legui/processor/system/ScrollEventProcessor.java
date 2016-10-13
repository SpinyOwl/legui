package org.liquidengine.legui.processor.system;

import javafx.scene.input.ScrollEvent;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseScrollEvent;
import org.liquidengine.legui.event.system.SystemScrollEvent;
import org.liquidengine.legui.processor.LeguiEventProcessor;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class ScrollEventProcessor extends SystemEventProcessor<SystemScrollEvent> {

    public ScrollEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemScrollEvent event, Component mainGui) {
        Component target = context.getMouseTargetGui();
        if (target != null) {
            callListeners(target, event);
            target.getProcessors().getScrollEventProcessor().process(target, event, context);
        }
    }

    private void callListeners(Component target, SystemScrollEvent event) {
        MouseScrollEvent scrollEvent = new MouseScrollEvent(target, event.xoffset, event.yoffset);
        LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) {
            leguiEventProcessor.pushEvent(scrollEvent);
        } else {
            target.getListenerList().getListeners(MouseScrollEvent.class).forEach(l -> l.update(scrollEvent));
        }
    }
}

package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.ScrollEvent;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class GuiScrollEventProcessor extends LeguiSystemEventProcessor<ScrollEvent> {

    public GuiScrollEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(ScrollEvent event, Component mainGui, Component target) {
        if (target != null) {
            callListeners(target, event);
            target.getProcessors().getScrollEventProcessor().process(target, event, context);
        }
    }

    private void callListeners(Component target, ScrollEvent event) {
//        List<ScrollListener> listeners = target.getListeners().getScrollListeners();
//        listeners.forEach(listener -> listener.onScroll(event.xoffset, event.yoffset));
    }
}

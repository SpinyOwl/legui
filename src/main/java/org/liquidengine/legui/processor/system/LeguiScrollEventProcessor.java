package org.liquidengine.legui.processor.system;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemScrollEvent;

/**
 * Created by Shcherbin Alexander on 8/30/2016.
 */
public class LeguiScrollEventProcessor extends LeguiSystemEventProcessor<SystemScrollEvent> {

    public LeguiScrollEventProcessor(LeguiContext context) {
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
//        List<ScrollListener> listeners = target.getListeners().getScrollListeners();
//        listeners.forEach(listener -> listener.onScroll(event.xoffset, event.yoffset));
    }
}

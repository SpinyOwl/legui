package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCharEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public class CharEventHandler implements SystemEventHandler<SystemCharEvent> {
    @Override
    public void process(SystemCharEvent event, Frame frame, Context context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) return;

        context.getEventProcessor().pushEvent(new CharEvent(focusedGui, context, event.codepoint));
    }
}

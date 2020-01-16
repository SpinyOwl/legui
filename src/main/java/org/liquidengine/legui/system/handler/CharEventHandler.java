package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCharEvent;

/**
 * Created by ShchAlexander on 2/14/2017.
 */
public class CharEventHandler implements SystemEventHandler<SystemCharEvent> {

    @Override
    public void handle(SystemCharEvent event, Frame frame, Context context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) {
            return;
        }

        EventProcessorProvider.getInstance().pushEvent(new CharEvent(focusedGui, context, frame, event.codepoint));
    }
}

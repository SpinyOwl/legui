package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.LeguiCharEvent;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemCharEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/14/2017.
 */
public class CharEventHandlerLegui implements LeguiSystemEventHandler<LeguiSystemCharEvent> {
    @Override
    public void handle(LeguiSystemCharEvent event, Frame frame, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (focusedGui == null) return;

        context.getEventProcessor().pushEvent(new LeguiCharEvent(focusedGui, context, event.codepoint));
    }
}

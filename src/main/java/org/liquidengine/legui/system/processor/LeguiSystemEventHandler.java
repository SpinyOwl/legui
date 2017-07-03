package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemEvent;

/**
 * Created by ShchAlexander on 10.02.2017.
 */
public interface LeguiSystemEventHandler<E extends LeguiSystemEvent> {
    void handle(E event, Frame frame, LeguiContext context);
}


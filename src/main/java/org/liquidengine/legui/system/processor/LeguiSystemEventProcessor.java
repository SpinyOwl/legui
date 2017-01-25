package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemEvent;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public interface LeguiSystemEventProcessor<E extends LeguiSystemEvent> {

    void process(E event, Frame frame, LeguiContext context);
}

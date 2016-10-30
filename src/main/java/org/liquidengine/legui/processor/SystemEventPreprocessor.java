package org.liquidengine.legui.processor;

import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.SystemEvent;

/**
 * Created by Alexander on 30.10.2016.
 */
public interface SystemEventPreprocessor<E extends SystemEvent> {
    void process(E event, LeguiContext context);
}

package org.liquidengine.legui.processor;

import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.SystemEvent;

/**
 * Created by Alexander on 31.10.2016.
 */
public interface SystemEventPostprocessor<E extends SystemEvent> {
    void process(E event, LeguiContext context);
}

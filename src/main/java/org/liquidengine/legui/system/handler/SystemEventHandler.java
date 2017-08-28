package org.liquidengine.legui.system.handler;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemEvent;

/**
 * Created by ShchAlexander on 10.02.2017.
 */
public interface SystemEventHandler<E extends SystemEvent> {

    void handle(E event, Frame frame, Context context);
}


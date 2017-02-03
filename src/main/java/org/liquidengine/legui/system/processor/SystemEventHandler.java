package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemEvent;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public interface SystemEventHandler<E extends SystemEvent> {

    void process(E event, Frame frame, Context context);
}

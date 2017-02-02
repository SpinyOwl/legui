package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemWindowRefreshEvent;

/**
 * Created by Aliaksandr_Shcherbin on 2/2/2017.
 */
public class WindowRefreshEventCallback implements SystemEventProcessor<SystemWindowRefreshEvent> {
    @Override
    public void process(SystemWindowRefreshEvent event, Frame frame, Context context) {
        System.out.println(event);
    }
}

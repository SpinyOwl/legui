package org.liquidengine.legui.processor.system.component.panel;

import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Alexander on 30.10.2016.
 */
public class PanelSystemMouseClickEventListener implements SystemEventListener<Panel, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, Panel component, LeguiContext context) {
        System.out.println("CLICK ON PANEL");
    }
}

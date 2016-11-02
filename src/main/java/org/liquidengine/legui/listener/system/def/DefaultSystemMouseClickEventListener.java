package org.liquidengine.legui.listener.system.def;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Alexander on 30.10.2016.
 */
public class DefaultSystemMouseClickEventListener implements SystemEventListener<Component, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, Component component, LeguiContext context) {
        System.out.println("DEF CLICK");
    }
}

package org.liquidengine.legui.listener.system.toggleButton;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.system.def.DefaultSystemMouseClickEventListener;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Alexander on 12.01.2017.
 */
public class ToggleButtonMouseClickEventProcessor implements SystemEventListener<ToggleButton, SystemMouseClickEvent> {

    private SystemEventListener<Component, SystemMouseClickEvent> defaultListener = new DefaultSystemMouseClickEventListener();

    @Override
    public void update(SystemMouseClickEvent event, ToggleButton component, LeguiContext context) {
        defaultListener.update(event, component, context);
        if (context.getFocusedGui() == component && event.action == GLFW.GLFW_RELEASE) {
            component.setToggled(!component.isToggled());
        }
    }
}

package org.liquidengine.legui.processor.post;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.SystemEventPostprocessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class SystemMouseClickEventPostprocessor implements SystemEventPostprocessor<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (event.action == GLFW.GLFW_RELEASE && focusedGui != null) {
            if (focusedGui != context.getMouseTargetGui()) {
                focusedGui.setFocused(false);
                focusedGui.setPressed(false);
            }
        }
    }

}

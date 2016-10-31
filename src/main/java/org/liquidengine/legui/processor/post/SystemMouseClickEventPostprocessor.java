package org.liquidengine.legui.processor.post;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.SystemEventPostprocessor;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class SystemMouseClickEventPostprocessor implements SystemEventPostprocessor<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, LeguiContext context) {
        if (event.action == GLFW.GLFW_RELEASE) {
            Component focusedGui = context.getFocusedGui();
            if (focusedGui != context.getMouseTargetGui()) {
                focusedGui.setFocused(false);
            }
//            release(context.getMainGuiComponent(), focusedGui, context.getMouseTargetGui(), context);
        }
    }

    public void release(Component gui, Component focused, Component mouseTargetGui, LeguiContext context) {
        System.out.println("RELEASE : " + gui.getClass().getSimpleName() + gui.hashCode() + " || " + focused.getClass().getSimpleName() + focused.hashCode());
        if (gui != focused) {
            gui.setFocused(false);
        }
        if (gui instanceof ComponentContainer) {
            ComponentContainer container = ((ComponentContainer) gui);
            List<Component> all = container.getComponents();
            for (Component element : all) {
                release(element, focused, mouseTargetGui, context);
            }
        }
    }
}

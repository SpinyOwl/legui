package org.liquidengine.legui.processor.pre;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.SystemEventPreprocessor;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class SystemMouseClickEventPreprocessor implements SystemEventPreprocessor<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, LeguiContext context) {
        context.getMouseButtonStates()[event.button] = event.action == GLFW.GLFW_PRESS;
        Component focusedGui = context.getFocusedGui();
        Component mouseTargetGui = context.getMouseTargetGui();
        if (focusedGui != null && focusedGui != mouseTargetGui) {
            focusedGui.setFocused(false);
            context.setFocusedGui(null);
        }
        if (mouseTargetGui != null) {
            mouseTargetGui.setFocused(true);
            context.setFocusedGui(mouseTargetGui);
        }
        if (event.action == GLFW.GLFW_RELEASE && focusedGui != null) {
            if (focusedGui != mouseTargetGui) {
                focusedGui.setFocused(false);
                focusedGui.setPressed(false);
            }
        }
        if (mouseTargetGui != null) {
            pushContainersUp(mouseTargetGui);
        }
    }

    private void pushContainersUp(Component gui) {
        Component parent = gui.getParent();
        Component current = gui;
        if (parent != null) {
            boolean push = false;
            while (parent != null) {
                push = parent instanceof Widget;
                current = parent;
                parent = parent.getParent();
                if (push) break;
            }
            if (push) {
                do {
                    if (parent instanceof ComponentContainer) {
                        ComponentContainer container = ((ComponentContainer) parent);
                        container.removeComponent(current);
                        container.addComponent(current);
                        current = parent;
                        parent = current.getParent();
                    }
                } while (parent != null);
            }
        }
    }
}

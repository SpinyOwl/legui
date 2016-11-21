package org.liquidengine.legui.processor.pre;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.FocusEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.LeguiEventListenerProcessor;
import org.liquidengine.legui.processor.SystemEventPreprocessor;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class SystemMouseClickEventPreprocessor implements SystemEventPreprocessor<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, LeguiContext context) {
        context.getMouseButtonStates()[event.button] = event.action == GLFW_PRESS;
        Component focusedGui = context.getFocusedGui();
        Component mouseTargetGui = context.getMouseTargetGui();

        if (event.action == GLFW_PRESS) {
            if (mouseTargetGui != null && focusedGui != mouseTargetGui) {

                if (focusedGui != null) {
                    focusedGui.getState().setFocused(false);
                    focusedGui.getState().setPressed(false);
                    processFocusEvent(context, focusedGui, mouseTargetGui, false);
                }

                mouseTargetGui.getState().setFocused(true);
                context.setFocusedGui(mouseTargetGui);
                processFocusEvent(context, mouseTargetGui, mouseTargetGui, true);
            }
        } else {
            if (focusedGui != null && focusedGui != mouseTargetGui) {
                focusedGui.getState().setPressed(false);
            }

//        } else {
//            if (focusedGui != null && focusedGui != mouseTargetGui) {
//                boolean focused = false;
//                focusedGui.getState().setFocused(false);
//                focusedGui.getState().setPressed(false);
//                processFocusEvent(context, focusedGui, focused);
//            }
        }

//        if (focusedGui != null && focusedGui != mouseTargetGui) {
//            boolean focused = false;
//            focusedGui.getState().setFocused(focused);
//            processFocusEvent(context, focusedGui, focused);
//            context.setFocusedGui(null);
//        }
        if (mouseTargetGui != null) {
            pushContainersUp(mouseTargetGui);
        }
    }

    private void processFocusEvent(LeguiContext context, Component focusedGui, Component focusTarget, boolean focused) {
        FocusEvent focusEvent = new FocusEvent(focusedGui, focused, focusTarget);
        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) leguiEventProcessor.pushEvent(focusEvent);
        else focusedGui.getLeguiEventListeners().getListeners(FocusEvent.class).forEach(l -> l.update(focusEvent));
    }

    private void pushContainersUp(Component gui) {
        ComponentContainer parent = gui.getParent();
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
                    ComponentContainer container =  parent;
                    if (container != null) {
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

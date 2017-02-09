package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.ScrollEvent;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;

import java.util.Collections;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class MouseClickEventHandler implements SystemEventHandler<SystemMouseClickEvent> {
    @Override
    public void process(SystemMouseClickEvent event, Frame frame, Context context) {
        preProcess(event, frame, context);
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            // if not visible or not enabled we should continue to next layer.
            if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;

            // else proceed with event.
            Controller targetController = SehUtil.getTargetController(layer, context.getCursorPosition());
            if (targetController != null) {
//                context.getEventProcessor().pushEvent(new ScrollEvent(targetController, event.xoffset, event.yoffset));
                return;
            }
            if (!layer.isEventPassable()) return;
        }
        postProcess(event, frame, context);
    }

    private void preProcess(SystemMouseClickEvent event, Frame frame, Context context) {
        if (event.action == GLFW_PRESS) {

        } else if (event.action == GLFW_RELEASE) {

        }
    }

    private void postProcess(SystemMouseClickEvent event, Frame frame, Context context) {

    }
//    private void p(){
//        context.getMouseButtonStates()[event.button] = event.action == GLFW_PRESS;
//        Component focusedGui     = context.getFocusedGui();
//        Component mouseTargetGui = context.getMouseTargetGui();
//
//        if (event.action == GLFW_PRESS) {
//            if (mouseTargetGui != null && focusedGui != mouseTargetGui) {
//
//                if (focusedGui != null) {
//                    focusedGui.getState().setFocused(false);
//                    focusedGui.getState().setPressed(false);
//                    processFocusEvent(context, focusedGui, mouseTargetGui, false);
//                }
//
//                mouseTargetGui.getState().setFocused(true);
//                context.setFocusedGui(mouseTargetGui);
//                processFocusEvent(context, mouseTargetGui, mouseTargetGui, true);
//            }
//        } else {
//            if (focusedGui != null && focusedGui != mouseTargetGui) {
//                focusedGui.getState().setPressed(false);
//            }
//        }
//        if (mouseTargetGui != null) {
//            pushContainersUp(mouseTargetGui);
//        }
//    }
//    private void processFocusEvent(Context context, Component focusedGui, Component focusTarget, boolean focused) {
//        FocusEvent          focusEvent          = new FocusEvent(focusedGui, focused, focusTarget);
//        LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
//        if (leguiEventProcessor != null) leguiEventProcessor.pushEvent(focusEvent);
//        else focusedGui.getLeguiEventListeners().getListeners(FocusEvent.class).forEach(l -> l.update(focusEvent));
//    }
//
//    private void pushContainersUp(Component gui) {
//        ComponentContainer parent  = gui.getParent();
//        Component          current = gui;
//        if (parent != null) {
//            boolean push = false;
//            while (parent != null) {
//                push = parent instanceof Widget;
//                current = parent;
//                parent = parent.getParent();
//                if (push) break;
//            }
//            if (push) {
//                parent.removeComponent(current);
//                parent.addComponent(current);
//            }
//        }
//    }
}

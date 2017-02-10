package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class MouseClickEventHandler extends AbstractSystemEventHandler<SystemMouseClickEvent> {

    @Override
    protected void preProcess(SystemMouseClickEvent event, Frame frame, Context context) {
        Mouse.MouseButton mouseButton = Mouse.MouseButton.getByCode(event.button);
        mouseButton.setPressed(event.action != GLFW_RELEASE);
        mouseButton.setPressPosition(Mouse.getCursorPosition());

        if (event.action == GLFW_PRESS) {

        } else if (event.action == GLFW_RELEASE) {

        }
    }

    @Override
    protected boolean process(SystemMouseClickEvent event, Layer layer, Context context) {

        if (event.action == GLFW.GLFW_PRESS) {
            Controller targetController = SehUtil.getTargetController(layer, Mouse.getCursorPosition());
            if (targetController != null) {
                context.setFocusedGui(targetController);
                return true;
            }
        }
        return false;
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

package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class MouseClickEventHandler implements SystemEventHandler<SystemMouseClickEvent> {

    @Override
    public void process(SystemMouseClickEvent event, Frame frame, Context context) {
        Mouse.MouseButton button = Mouse.MouseButton.getByCode(event.button);
        button.setPressed(event.action != GLFW_RELEASE);
        Vector2f cursorPosition = Mouse.getCursorPosition();
        button.setPressPosition(cursorPosition);

        List<Layer> layers = frame.getLayers();
        layers.add(frame.getComponentLayer());
        Collections.reverse(layers);

        Controller focusedGui       = context.getFocusedGui();
        Controller targetController = null;
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;
                targetController = SehUtil.getTargetController(layer, cursorPosition);
                if (targetController != null) break;
            }
            if (!layer.isEventPassable()) break;
        }
        if (event.action == GLFW.GLFW_PRESS) {
            button.setPressPosition(cursorPosition);
            removeFocus(targetController, frame, context);
            if (targetController != null) {
                targetController.setPressed(true);
                if (focusedGui != targetController) {
                    targetController.setFocused(true);
                    context.setFocusedGui(targetController);
                    context.getEventProcessor().pushEvent(new FocusEvent(targetController,targetController, true));
                }
                Vector2f position = targetController.getScreenPosition().sub(cursorPosition).negate();
                context.getEventProcessor().pushEvent(new MouseClickEvent(targetController, MouseClickEvent.MOUSE_PRESS, button, position, cursorPosition));
            }
        } else {
            button.setReleasePosition(cursorPosition);
            if (focusedGui != null) {
                focusedGui.setPressed(false);
            }

            Vector2f position = targetController.getScreenPosition().sub(cursorPosition).negate();
            if(focusedGui!=null && focusedGui==targetController) {
                context.getEventProcessor().pushEvent(new MouseClickEvent(targetController, MouseClickEvent.CLICK, button, position, cursorPosition));
            }
            context.getEventProcessor().pushEvent(new MouseClickEvent(targetController, MouseClickEvent.MOUSE_RELEASE, button, position, cursorPosition));
        }
        if (targetController != null) {
            pushWidgetsUp(targetController);
        }
    }

    private void removeFocus(Controller targetController, Frame frame, Context context) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer layer : allLayers) {
            List<Component> childs = layer.getContainer().getChilds();
            for (Component child : childs) {
                if (child instanceof Controller) {
                    removeFocus(targetController, (Controller) child, context);
                }
            }
        }
    }

    private void removeFocus(Controller focused, Controller controller, Context context) {
        if (controller != focused) {
            if (controller.isVisible()) {
                if (controller.isFocused()) {
                    controller.setFocused(false);
                    controller.setPressed(false);
                    context.getEventProcessor().pushEvent(new FocusEvent(controller, focused, false));
                }
            }
        }
        if (controller instanceof Container) {
            List<Component> childs = ((Container) controller).getChilds();
            for (Component child : childs) {
                if (child instanceof Controller) {
                    removeFocus(focused, (Controller) child, context);
                }
            }
        }
    }

    private void pushWidgetsUp(Controller gui) {
        Container  parent  = gui.getParent();
        Controller current = gui;
        if (parent != null) {
            boolean push = false;
            while (parent != null) {
                push = parent instanceof Widget;
                current = parent;
                parent = parent.getParent();
                if (push) break;
            }
            if (push) {
                parent.remove(current);
                parent.add(current);
            }
        }
    }
}

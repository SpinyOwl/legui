package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class MouseClickEventHandler implements SystemEventHandler<SystemMouseClickEvent> {

    @Override
    public void handle(SystemMouseClickEvent event, Frame frame, Context context) {
        Mouse.MouseButton button = Mouse.MouseButton.getByCode(event.button);
        button.setPressed(event.action != GLFW_RELEASE);
        Vector2f cursorPosition = Mouse.getCursorPosition();
        button.setPressPosition(cursorPosition);

        List<Layer> layers = frame.getLayers();
        layers.add(frame.getComponentLayer());

        Component focusedGui      = context.getFocusedGui();
        Component targetComponent = null;
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;
                targetComponent = SehUtil.getTargetComponent(layer, cursorPosition);
                if (targetComponent != null) break;
            }
            if (!layer.isEventPassable()) break;
        }
        if (targetComponent == null) {
            context.setFocusedGui(null);
            return;
        }
        if (event.action == GLFW.GLFW_PRESS) {
            button.setPressPosition(cursorPosition);
            removeFocus(targetComponent, frame, context);
            targetComponent.setPressed(true);
            if (focusedGui != targetComponent) {
                targetComponent.setFocused(true);
                context.setFocusedGui(targetComponent);
                context.getEventProcessor().pushEvent(new FocusEvent(targetComponent, context, targetComponent, true));
            }
            Vector2f position = targetComponent.getScreenPosition().sub(cursorPosition).negate();
            context.getEventProcessor().pushEvent(new MouseClickEvent(targetComponent, context, MouseClickEvent.MouseClickAction.PRESS, button, position, cursorPosition));
        } else {
            button.setReleasePosition(cursorPosition);
            if (focusedGui != null) {
                focusedGui.setPressed(false);
            }

            Vector2f position = targetComponent.getScreenPosition().sub(cursorPosition).negate();
            if (focusedGui != null && focusedGui == targetComponent) {
                context.getEventProcessor().pushEvent(new MouseClickEvent(targetComponent, context, MouseClickEvent.MouseClickAction.CLICK, button, position, cursorPosition));
            }
            context.getEventProcessor().pushEvent(new MouseClickEvent(targetComponent, context, MouseClickEvent.MouseClickAction.RELEASE, button, position, cursorPosition));
        }
        pushWidgetsUp(targetComponent);
    }

    private void removeFocus(Component targetComponent, Frame frame, Context context) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer layer : allLayers) {
            List<Component> childs = layer.getContainer().getChilds();
            for (Component child : childs) {
                removeFocus(targetComponent, child, context);
            }
        }
    }

    private void removeFocus(Component focused, Component component, Context context) {
        if (component != focused) {
            if (component.isVisible()) {
                if (component.isFocused()) {
                    component.setFocused(false);
                    component.setPressed(false);
                    context.getEventProcessor().pushEvent(new FocusEvent(component, context, focused, false));
                }
            }
        }
        if (component instanceof Container) {
            List<Component> childs = ((Container) component).getChilds();
            for (Component child : childs) {
                removeFocus(focused, child, context);
            }
        }
    }

    private void pushWidgetsUp(Component gui) {
        Container parent  = gui.getParent();
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
                parent.remove(current);
                parent.add(current);
            }
        }
    }
}

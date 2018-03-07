package org.liquidengine.legui.system.handler;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.List;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.lwjgl.glfw.GLFW;

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

        Component focusedGui = context.getFocusedGui();
        Component targetComponent = null;
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) {
                    continue;
                }
                targetComponent = SehUtil.getTargetComponent(layer, cursorPosition);
                if (targetComponent != null) {
                    break;
                }
            }
            if (!layer.isEventPassable()) {
                break;
            }
        }
        if (targetComponent == null) {
            if (event.action == GLFW_RELEASE) {
                updateReleasePosAndFocusedGui(button, cursorPosition, focusedGui);
            } else {
                context.setFocusedGui(null);
            }
        } else {
            if (event.action == GLFW.GLFW_PRESS) {
                button.setPressPosition(cursorPosition);
                removeFocus(targetComponent, frame, context);
                targetComponent.setPressed(true);
                if (focusedGui != targetComponent) {
                    targetComponent.setFocused(true);
                    context.setFocusedGui(targetComponent);
                }
                Vector2f position = targetComponent.getAbsolutePosition().sub(cursorPosition).negate();
                EventProcessor.getInstance()
                    .pushEvent(
                        new MouseClickEvent<>(targetComponent, context, frame, MouseClickEvent.MouseClickAction.PRESS, button, position, cursorPosition));

                if (focusedGui != targetComponent) {
                    EventProcessor.getInstance().pushEvent(new FocusEvent<>(targetComponent, context, frame, targetComponent, true));
                }
            } else {
                updateReleasePosAndFocusedGui(button, cursorPosition, focusedGui);

                Vector2f position = targetComponent.getAbsolutePosition().sub(cursorPosition).negate();
                if (focusedGui != null && focusedGui == targetComponent) {
                    EventProcessor.getInstance().pushEvent(
                        new MouseClickEvent<>(targetComponent, context, frame, MouseClickEvent.MouseClickAction.CLICK, button, position, cursorPosition));
                }
                EventProcessor.getInstance().pushEvent(
                    new MouseClickEvent<>(targetComponent, context, frame, MouseClickEvent.MouseClickAction.RELEASE, button, position, cursorPosition));
            }
            pushWidgetsUp(targetComponent);
        }
    }

    private void updateReleasePosAndFocusedGui(Mouse.MouseButton button, Vector2f cursorPosition, Component focusedGui) {
        button.setReleasePosition(cursorPosition);
        if (focusedGui != null) {
            focusedGui.setPressed(false);
        }
    }

    private void removeFocus(Component targetComponent, Frame frame, Context context) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer layer : allLayers) {
            List<Component> childComponents = layer.getContainer().getChildComponents();
            for (Component child : childComponents) {
                removeFocus(targetComponent, child, context, frame);
            }
        }
    }

    private void removeFocus(Component focused, Component component, Context context, Frame frame) {
        if (component != focused && component.isVisible() && component.isFocused()) {
            component.setFocused(false);
            component.setPressed(false);
            EventProcessor.getInstance().pushEvent(new FocusEvent<>(component, context, frame, focused, false));
        }
        List<? extends Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            removeFocus(focused, child, context, frame);
        }
    }

    private void pushWidgetsUp(Component gui) {
        Component parent = gui.getParent();
        Component current = gui;
        if (parent != null) {
            boolean push = false;
            while (parent != null) {
                push = (parent instanceof Widget) && (parent.getParent()!=null) && (parent.getParent().getStyle().getDisplay()== DisplayType.NONE);
                current = parent;
                parent = parent.getParent();
                if (push) {
                    break;
                }
            }
            if (push) {
                parent.remove(current);
                parent.add(current);
            }
        }
    }
}

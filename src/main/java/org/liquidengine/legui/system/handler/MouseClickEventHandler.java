package org.liquidengine.legui.system.handler;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.PRESS;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.Collections;
import java.util.List;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.event.FocusEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.lwjgl.glfw.GLFW;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class MouseClickEventHandler implements SystemEventHandler<SystemMouseClickEvent> {

    @Override
    public void handle(SystemMouseClickEvent event, Frame frame, Context ctx) {
        Mouse.MouseButton btn = Mouse.MouseButton.getByCode(event.button);
        btn.setPressed(event.action != GLFW_RELEASE);
        Vector2f cursorPos = Mouse.getCursorPosition();
        btn.setPressPosition(cursorPos);

        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);

        Component focusedGui = ctx.getFocusedGui();
        Component target     = null;
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) {
                    continue;
                }
                target = SehUtil.getTargetComponent(layer, cursorPos);
                if (target != null) {
                    break;
                }
            }
            if (!layer.isEventPassable()) {
                break;
            }
        }
        if (target == null) {
            if (event.action == GLFW_RELEASE) {
                updateReleasePosAndFocusedGui(btn, cursorPos, focusedGui);
            } else {
                ctx.setFocusedGui(null);
            }
        } else {
            int mods = event.mods;
            if (event.action == GLFW.GLFW_PRESS) {
                btn.setPressPosition(cursorPos);
                removeFocus(target, frame, ctx);
                target.setPressed(true);

                if (focusedGui != target) {
                    target.setFocused(true);
                    ctx.setFocusedGui(target);
                }

                Vector2f position = target.getAbsolutePosition().sub(cursorPos).negate();
                EventProcessorProvider.getInstance().pushEvent(new MouseClickEvent<>(target, ctx, frame, PRESS, btn, position, cursorPos, mods));

                if (focusedGui != target) {
                    EventProcessorProvider.getInstance().pushEvent(new FocusEvent<>(target, ctx, frame, target, true));
                }
            } else {
                updateReleasePosAndFocusedGui(btn, cursorPos, focusedGui);

                Vector2f pos = target.getAbsolutePosition().sub(cursorPos).negate();
                if (focusedGui != null && focusedGui == target) {
                    EventProcessorProvider.getInstance().pushEvent(new MouseClickEvent<>(target, ctx, frame, CLICK, btn, pos, cursorPos, mods));
                }
                EventProcessorProvider.getInstance().pushEvent(new MouseClickEvent<>(target, ctx, frame, RELEASE, btn, pos, cursorPos, mods));
            }
            pushWidgetsUp(target);
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
            EventProcessorProvider.getInstance().pushEvent(new FocusEvent<>(component, context, frame, focused, false));
        }
        List<? extends Component> childComponents = component.getChildComponents();
        for (Component child : childComponents) {
            removeFocus(focused, child, context, frame);
        }
    }

    private void pushWidgetsUp(Component gui) {
        Component parent  = gui.getParent();
        Component current = gui;
        if (parent != null) {
            boolean push = false;
            while (parent != null) {
                push    = (parent instanceof Widget) && (parent.getParent() != null) && (parent.getParent().getStyle().getDisplay() == DisplayType.MANUAL);
                current = parent;
                parent  = parent.getParent();
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

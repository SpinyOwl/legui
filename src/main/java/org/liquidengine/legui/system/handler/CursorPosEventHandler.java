package org.liquidengine.legui.system.handler;

import java.util.Collections;
import java.util.List;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;

/**
 * Cursor position event handler.
 */
public class CursorPosEventHandler extends AbstractSystemEventHandler<SystemCursorPosEvent> {

    /**
     * Pre-handles {@link SystemCursorPosEvent} event.
     *
     * @param event   event which should be pre-processed.
     * @param frame   target frame for event.
     * @param context context
     */
    protected void preHandle(SystemCursorPosEvent event, Frame frame, Context context) {
        Vector2f cursorPosition = new Vector2f(event.fx, event.fy);
        Mouse.setCursorPositionPrev(new Vector2f(Mouse.getCursorPosition()));
        Mouse.setCursorPosition(cursorPosition);

        List<Layer> allLayers = frame.getAllLayers();
        Collections.reverse(allLayers);
        Component targetComponent = null;
        for (Layer layer : allLayers) {
            if (!layer.isEventReceivable() || !layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) {
                continue;
            }
            targetComponent = SehUtil.getTargetComponent(layer, cursorPosition);
            if (targetComponent != null || !layer.isEventPassable()) {
                break;
            }
        }
        Component prevTarget = context.getMouseTargetGui();
        context.setMouseTargetGui(targetComponent);
        if (targetComponent != prevTarget) {
            if (targetComponent != null) {
                targetComponent.setHovered(true);
                Vector2f curPosInComponent = targetComponent.getAbsolutePosition().sub(cursorPosition).negate();
                CursorEnterEvent enterEvent = new CursorEnterEvent(targetComponent, context, frame, true, curPosInComponent, cursorPosition);
                EventProcessorProvider.getInstance().pushEvent(enterEvent);
            }
            if (prevTarget != null) {
                Vector2f curPosInPrevTarget = prevTarget.getAbsolutePosition().sub(cursorPosition).negate();
                CursorEnterEvent exitEvent = new CursorEnterEvent(prevTarget, context, frame, false, curPosInPrevTarget, cursorPosition);
                EventProcessorProvider.getInstance().pushEvent(exitEvent);
                prevTarget.setHovered(false);
            }
        }
    }

    /**
     * Used to handle {@link SystemCursorPosEvent} and produce (or not) {@link Event} instances (which are UI events).
     *
     * @param event   event to be processed.
     * @param layer   target event layer.
     * @param context context.
     * @param frame   frame.
     * @return true if event processed and it shouldn't be processed for other underlying layers.
     */
    @Override
    protected boolean handle(SystemCursorPosEvent event, Layer layer, Context context, Frame frame) {
        List<Component> childComponents = layer.getContainer().getChildComponents();
        for (Component child : childComponents) {
            handle(child, context, frame);
        }
        return false;
    }

    /**
     * Used to handle event for specific component.
     *
     * @param component component.
     * @param context   context.
     * @param frame     frame.
     */
    private void handle(Component component, Context context, Frame frame) {
        if (component.isEmpty()) {
            if ((Mouse.MouseButton.MOUSE_BUTTON_LEFT.isPressed() || Mouse.MouseButton.MOUSE_BUTTON_RIGHT.isPressed()) &&
	            component == context.getFocusedGui()) {
                Vector2f delta = Mouse.getCursorPosition().sub(Mouse.getCursorPositionPrev());
                EventProcessorProvider.getInstance().pushEvent(new MouseDragEvent(component, context, frame, delta));
            }
        } else {
            List<Component> childComponents = component.getChildComponents();
            for (Component child : childComponents) {
                handle(child, context, frame);
            }
        }
    }

}

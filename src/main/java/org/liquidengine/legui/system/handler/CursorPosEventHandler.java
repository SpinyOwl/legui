package org.liquidengine.legui.system.handler;

import java.util.Collections;
import java.util.List;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.processor.EventProcessor;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class CursorPosEventHandler extends AbstractSystemEventHandler<SystemCursorPosEvent> {

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
        if (targetComponent != null) {
            Component prevTarget = context.getMouseTargetGui();
            if (targetComponent != prevTarget) {
                context.setMouseTargetGui(targetComponent);
                targetComponent.setHovered(true);
                Vector2f curPosInComponent = targetComponent.getAbsolutePosition().sub(cursorPosition).negate();
                CursorEnterEvent enterEvent = new CursorEnterEvent(targetComponent, context, frame, true, curPosInComponent, cursorPosition);
                EventProcessor.getInstance().pushEvent(enterEvent);
                if (prevTarget != null) {
                    Vector2f curPosInPrevTarget = prevTarget.getAbsolutePosition().sub(cursorPosition).negate();
                    CursorEnterEvent exitEvent = new CursorEnterEvent(prevTarget, context, frame, false, curPosInPrevTarget, cursorPosition);
                    EventProcessor.getInstance().pushEvent(exitEvent);
                    prevTarget.setHovered(false);
                }
            }
        }
    }

    @Override
    protected boolean handle(SystemCursorPosEvent event, Layer layer, Context context, Frame frame) {
        List<Component> childs = layer.getContainer().getChilds();
        for (Component child : childs) {
            update(event, child, context, frame);
        }
        return false;
    }

    private void update(SystemCursorPosEvent event, Component component, Context context, Frame frame) {
        if (component instanceof Container) {
            processAsContainer(event, component, context, frame);
        } else {
            processAsComponent(component, context, frame);
        }
    }

    private void processAsContainer(SystemCursorPosEvent event, Component component, Context context, Frame frame) {
        Container container = (Container) component;
        if (container.isEmpty()) {
            processAsComponent(component, context, frame);
        } else {
            List<Component> childs = container.getChilds();
            for (Component child : childs) {
                update(event, child, context, frame);
            }
        }
    }

    private void processAsComponent(Component component, Context context, Frame frame) {
        if (Mouse.MouseButton.MOUSE_BUTTON_1.isPressed() && component == context.getFocusedGui()) {
            Vector2f delta = Mouse.getCursorPosition().sub(Mouse.getCursorPositionPrev());
            EventProcessor.getInstance().pushEvent(new MouseDragEvent(component, context, frame, delta));
        }
    }
}

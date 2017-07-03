package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.event.LeguiCursorEnterEvent;
import org.liquidengine.legui.event.LeguiMouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.LeguiEventProcessor;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.event.LeguiSystemCursorPosEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class CursorPosEventHandlerLegui extends AbstractLeguiSystemEventHandler<LeguiSystemCursorPosEvent> {

    protected void preHandle(LeguiSystemCursorPosEvent event, Frame frame, LeguiContext context) {
        Vector2f cursorPosition = new Vector2f(event.fx, event.fy);
        Mouse.setCursorPositionPrev(new Vector2f(Mouse.getCursorPosition()));
        Mouse.setCursorPosition(cursorPosition);

        List<Layer> allLayers = frame.getAllLayers();
        Collections.reverse(allLayers);
        Component targetComponent = null;
        for (Layer layer : allLayers) {
            targetComponent = LeguiSystemEventHandlerUtil.getTargetComponent(layer, cursorPosition);
            if (targetComponent != null || !layer.isEventPassable()) break;
        }
        if (targetComponent != null) {
            Component prevTarget = context.getMouseTargetGui();
            if (targetComponent != prevTarget) {
                context.setMouseTargetGui(targetComponent);
                targetComponent.setHovered(true);
                Vector2f curPosInComponent = targetComponent.getScreenPosition().sub(cursorPosition).negate();
                context.getEventProcessor().pushEvent(new LeguiCursorEnterEvent(targetComponent, context, true, curPosInComponent, cursorPosition));
                if (prevTarget != null) {
                    Vector2f curPosInPrevTarget = prevTarget.getScreenPosition().sub(cursorPosition).negate();
                    context.getEventProcessor().pushEvent(new LeguiCursorEnterEvent(prevTarget, context, false, curPosInPrevTarget, cursorPosition));
                    prevTarget.setHovered(false);
                }
            }
        }
    }

    @Override
    protected boolean handle(LeguiSystemCursorPosEvent event, Layer layer, LeguiContext context) {
        List<Component> childs = layer.getContainer().getChilds();
        for (Component child : childs) {
            update(event, child, context);
        }
        return false;
    }

    private void update(LeguiSystemCursorPosEvent event, Component component, LeguiContext context) {
        if (component instanceof Container) {
            processAsContainer(event, component, context);
        } else {
            processAsComponent(component, context);
        }
    }

    private void processAsContainer(LeguiSystemCursorPosEvent event, Component component, LeguiContext context) {
        Container container = (Container) component;
        if (container.isEmpty()) {
            processAsComponent(component, context);
        } else {
            List<Component> childs = container.getChilds();
            for (Component child : childs) {
                update(event, child, context);
            }
        }
    }

    private void processAsComponent(Component component, LeguiContext context) {
        LeguiEventProcessor eventProcessor = context.getEventProcessor();
        if (Mouse.MouseButton.MOUSE_BUTTON_1.isPressed() && component == context.getFocusedGui()) {
            Vector2f delta = Mouse.getCursorPosition().sub(Mouse.getCursorPositionPrev());
            eventProcessor.pushEvent(new LeguiMouseDragEvent(component, context, delta));
        }
    }
}

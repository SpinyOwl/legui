package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseDragEvent;
import org.liquidengine.legui.input.Mouse;
import org.liquidengine.legui.listener.EventProcessor;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by ShchAlexander on 04.02.2017.
 */
public class CursorPosEventHandler extends AbstractSystemEventHandler<SystemCursorPosEvent> {

    protected void preProcess(SystemCursorPosEvent event, Frame frame, Context context) {
        Vector2f cursorPosition = new Vector2f(event.fx, event.fy);
        Mouse.setCursorPositionPrev(new Vector2f(Mouse.getCursorPosition()));
        Mouse.setCursorPosition(cursorPosition);

        List<Layer> allLayers = frame.getAllLayers();
        Collections.reverse(allLayers);
        Controller targetController = null;
        for (Layer layer : allLayers) {
            targetController = SehUtil.getTargetController(layer, cursorPosition);
            if (targetController != null || !layer.isEventPassable()) break;
        }
        context.setMouseTargetGui(targetController);
    }

    @Override
    protected boolean process(SystemCursorPosEvent event, Layer layer, Context context) {
        List<Component> childs = layer.getContainer().getChilds();
        for (Component child : childs) {
            if (child instanceof Controller) {
                update(event, (Controller) child, context);
            }
        }
        return false;
    }

    @Override
    protected void postProcess(SystemCursorPosEvent event, Frame frame, Context context) {

    }

    private void update(SystemCursorPosEvent event, Controller controller, Context context) {
        if (controller instanceof Container) {
            processAsContainer(event, controller, context);
        } else {
            processAsController(controller, context);
        }
    }

    private void processAsContainer(SystemCursorPosEvent event, Controller controller, Context context) {
        Container container = (Container) controller;
        if (container.isEmpty()) {
            processAsController(controller, context);
        } else {
            List<Component> childs = container.getChilds();
            for (Component child : childs) {
                if (child instanceof Controller) {
                    update(event, (Controller) child, context);
                }
            }
        }
    }

    private void processAsController(Controller controller, Context context) {
        EventProcessor eventProcessor = context.getEventProcessor();
        if (Mouse.MouseButton.MOUSE_BUTTON_1.isPressed() && controller == context.getFocusedGui()) {
            Vector2f delta = Mouse.getCursorPosition().sub(Mouse.getCursorPositionPrev());
            eventProcessor.pushEvent(new MouseDragEvent(controller, delta));
        }
    }
//    /**
//     * Updates standard context of frame element
//     *
//     * @param event
//     * @param component
//     * @param context
//     */
//    private void updateComponentStatesAndCallListeners(SystemCursorPosEvent event, Controller controller, Context context) {
//        EventProcessor eventProcessor = context.getEventProcessor();
//        if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && component == context.getFocusedGui()) {
//            List<LeguiEventListener<MouseDragEvent>> mouseDragEventListeners = component.getLeguiEventListeners().getListeners(MouseDragEvent.class);
//            MouseDragEvent                           mouseDragEvent          = new MouseDragEvent(new Vector2f(event.fx, event.fy), context.getCursorPositionPrev(), component);
//            if (eventProcessor == null) {
//                mouseDragEventListeners.forEach(l -> l.update(mouseDragEvent));
//            } else {
//                eventProcessor.pushEvent(mouseDragEvent);
//            }
//        }
//
//        List<LeguiEventListener<CursorEnterEvent>> listeners        = component.getLeguiEventListeners().getListeners(CursorEnterEvent.class);
//        Vector2f                                   position         = Util.calculatePosition(component);
//        Vector2f                                   cursorPosition   = context.getCursorPosition();
//        boolean                                    intersects       = component.getIntersector().intersects(component, cursorPosition);
//        Vector2f                                   mousePosition    = position.sub(cursorPosition).negate();
//        boolean                                    update           = false;
//        CursorEnterEvent                           cursorEnterEvent = null;
//
//        if (component.getState().isHovered()) {
//            if (!intersects || component != context.getMouseTargetGui()) {
//                component.getState().setHovered(false);
//                cursorEnterEvent = new CursorEnterEvent(component, CursorEnterEvent.CursorEnterAction.EXIT, mousePosition);
//                if (component.getTooltipText() != null) {
//                    context.getFrame().getTooltipLayer().removeComponent(component.getTooltip());
//                }
//                update = true;
//            }
//        } else if (!component.getState().isHovered() && intersects && component == context.getMouseTargetGui()) {
//            component.getState().setHovered(true);
//            cursorEnterEvent = new CursorEnterEvent(component, CursorEnterEvent.CursorEnterAction.ENTER, mousePosition);
//            if (component.getTooltipText() != null) {
//                context.getFrame().getTooltipLayer().addComponent(component.getTooltip());
//            }
//            update = true;
//        }
//        if (update) {
//            if (leguiEventProcessor == null) {
//                CursorEnterEvent finalCursorEnterEvent = cursorEnterEvent;
//                listeners.forEach(listener -> listener.update(finalCursorEnterEvent));
//            } else {
//                leguiEventProcessor.pushEvent(cursorEnterEvent);
//            }
//        }
//    }
}

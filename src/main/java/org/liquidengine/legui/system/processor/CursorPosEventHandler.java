package org.liquidengine.legui.system.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
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
public class CursorPosEventHandler implements SystemEventHandler<SystemCursorPosEvent> {
    @Override
    public void process(SystemCursorPosEvent event, Frame frame, Context context) {
        preProcess(event, frame, context);
        processEvent(event, frame, context);

    }

    private void preProcess(SystemCursorPosEvent event, Frame frame, Context context) {
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

    private void processEvent(SystemCursorPosEvent event, Frame frame, Context context) {
        List<Layer> allLayers = frame.getLayers();
        Collections.reverse(allLayers);
        for (Layer layer : allLayers) {
            processLayer(event, context, layer);
            if (!layer.isEventPassable()) return;
        }
        processLayer(event, context, frame.getComponentLayer());
    }

    private void processLayer(SystemCursorPosEvent event, Context context, Layer layer) {
        List<Component> childs = layer.getContainer().getChilds();
        for (Object child : childs) {
            if (child instanceof Controller) {
                update(event, (Controller) child, context);
            }
        }
    }

    private void update(SystemCursorPosEvent event, Controller controller, Context context) {
        EventProcessor eventProcessor = context.getEventProcessor();
        if (Mouse.MouseButton.MOUSE_BUTTON_1.isPressed() && controller == context.getFocusedGui()) {
            Vector2f delta = Mouse.getCursorPosition().sub(Mouse.getCursorPositionPrev());
            eventProcessor.pushEvent(new MouseDragEvent(controller, delta));
            System.out.println("DRAG");
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

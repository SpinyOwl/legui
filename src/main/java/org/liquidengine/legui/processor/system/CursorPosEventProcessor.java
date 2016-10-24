//package org.liquidengine.legui.processor.system;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.component.Component;
//import org.liquidengine.legui.component.ComponentContainer;
//import org.liquidengine.legui.context.LeguiContext;
//import org.liquidengine.legui.event.component.CursorEnterEvent;
//import org.liquidengine.legui.event.component.MouseDragEvent;
//import org.liquidengine.legui.event.system.SystemCursorPosEvent;
//import org.liquidengine.legui.listener.LeguiEventListener;
//import org.liquidengine.legui.processor.LeguiEventListenerProcessor;
//import org.liquidengine.legui.util.Util;
//import org.lwjgl.glfw.GLFW;
//
//import java.util.List;
//
///**
// * Event processor for cursor events. Updates GUI element depending on cursor position. Calls CursorEventListeners of GUI element
// * Created by Shcherbin Alexander on 6/16/2016.
// */
//public class CursorPosEventProcessor extends SystemEventProcessor<SystemCursorPosEvent> {
//
//    public CursorPosEventProcessor(LeguiContext context) {
//        super(context);
//    }
//
//    @Override
//    public void processEvent(SystemCursorPosEvent event, Component mainGui) {
//        Component target = context.getMouseTargetGui();
//        context.setCursorPositionPrev(context.getCursorPosition());
//        context.setCursorPosition(new Vector2f(event.fx, event.fy));
//        process(event, mainGui, target);
//    }
//
//    private void process(SystemCursorPosEvent event, Component gui, Component target) {
////        gui.getProcessors().getListener(event.getClass()).update(gui, event, context);
//        updateComponentStatesAndCallListeners(event, gui, target);
//        if (gui instanceof ComponentContainer) {
//            processEventOnContainer(event, gui, target);
//        }
//    }
//
//
//    private void processEventOnContainer(SystemCursorPosEvent event, Component gui, Component target) {
//        ComponentContainer container = ((ComponentContainer) gui);
//        List<Component> all = container.getComponents();
//        for (Component element : all) {
//            process(event, element, target);
//        }
//    }
//
//    /**
//     * Updates standard context of gui element
//     *
//     * @param event
//     * @param gui
//     */
//    private void updateComponentStatesAndCallListeners(SystemCursorPosEvent event, Component gui, Component target) {
//        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
//        if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && gui == context.getFocusedGui()) {
//            List<LeguiEventListener<MouseDragEvent>> mouseDragEventListeners = gui.getListenerList().getListeners(MouseDragEvent.class);
//            MouseDragEvent mouseDragEvent = new MouseDragEvent(new Vector2f(event.fx, event.fy), context.getCursorPositionPrev(), gui);
//            if (leguiEventProcessor == null) {
//                mouseDragEventListeners.forEach(l -> l.update(mouseDragEvent));
//            } else {
//                leguiEventProcessor.pushEvent(mouseDragEvent);
//            }
//        }
//
//        List<LeguiEventListener<CursorEnterEvent>> listeners = gui.getListenerList().getListeners(CursorEnterEvent.class);
//        Vector2f position = Util.calculatePosition(gui);
//        Vector2f cursorPosition = context.getCursorPosition();
//        boolean intersects = gui.getIntersector().intersects(gui, cursorPosition);
//        Vector2f mousePosition = position.sub(cursorPosition).negate();
//        boolean update = false;
//        CursorEnterEvent cursorEnterEvent = null;
//        if (gui.isHovered()) {
//            if (!intersects || gui != target) {
//                gui.setHovered(false);
//                cursorEnterEvent = new CursorEnterEvent(gui, CursorEnterEvent.CursorEnterAction.EXIT, mousePosition);
//                update = true;
//            }
//        } else if (!gui.isHovered() && intersects && gui == target) {
//            gui.setHovered(true);
//            cursorEnterEvent = new CursorEnterEvent(gui, CursorEnterEvent.CursorEnterAction.ENTER, mousePosition);
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
//}

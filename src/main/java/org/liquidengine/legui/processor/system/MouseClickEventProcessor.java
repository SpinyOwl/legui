//package org.liquidengine.legui.processor.system;
//
//import org.joml.Vector2f;
//import org.liquidengine.legui.component.Component;
//import org.liquidengine.legui.component.ComponentContainer;
//import org.liquidengine.legui.component.Widget;
//import org.liquidengine.legui.component.intersector.LeguiIntersector;
//import org.liquidengine.legui.context.LeguiContext;
//import org.liquidengine.legui.event.component.MouseClickEvent;
//import org.liquidengine.legui.event.system.SystemMouseClickEvent;
//import org.liquidengine.legui.processor.EventProcessorUtils;
//import org.liquidengine.legui.processor.LeguiEventListenerProcessor;
//import org.liquidengine.legui.util.Util;
//
//import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.*;
//import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
//import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
//
///**
// * Processor for mouse click events
// * Created by Alexander on 16.06.2016.
// */
//public class MouseClickEventProcessor extends SystemEventProcessor<SystemMouseClickEvent> {
//
//    public MouseClickEventProcessor(LeguiContext context) {
//        super(context);
//    }
//
//    @Override
//    public void processEvent(SystemMouseClickEvent event, Component mainGui) {
//        Vector2f cursorPosition = context.getCursorPosition();
//        context.getMouseButtonStates()[event.button] = event.action == GLFW_PRESS;
//        context.getMouseButtonPressPosition()[event.button] = event.action == GLFW_PRESS ? new Vector2f(cursorPosition) : null;
//
//        Component target = context.getMouseTargetGui();
//        if (target != null) {
//            process(event, mainGui, target);
//        }
//    }
//
//
//    private void pushUp(Component gui) {
//        Component parent = gui.getParent();
//        if (parent != null) {
//            boolean push = false;
//            while (parent != null) {
//                push = parent instanceof Widget;
//                parent = parent.getParent();
//                if (push) break;
//            }
//
//            if (push) {
//                parent = gui.getParent();
//                do {
//                    if (parent instanceof ComponentContainer) {
//                        ComponentContainer container = ((ComponentContainer) parent);
//                        container.removeComponent(gui);
//                        container.addComponent(gui);
//                        gui = parent;
//                        parent = gui.getParent();
//                    }
//                } while (parent != null);
//            }
//        }
//    }
//
//    private void process(SystemMouseClickEvent event, Component mainComponent, Component component) {
//        Component focusedGui = context.getFocusedGui();
//        if (!component.equals(focusedGui)) component.setFocused(false);
//
//
////        component.getProcessors().getMouseClickEventProcessor().process(component, event, context);
//
//
//        boolean intersects = intersects(component);
//        Vector2f cursorPosition = context.getCursorPosition();
//        if (event.action == GLFW_PRESS) {
//            if (intersects) {
//                pushUp(component);
//                context.setFocusedGui(component);
//                component.setFocused(true);
//                component.setPressed(true);
//                EventProcessorUtils.release(mainComponent, component);
//                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
//                processEvent(component, new MouseClickEvent(component, position, PRESS, event.button));
//            } else {
//                component.setPressed(false);
//            }
//        } else if (event.action == GLFW_RELEASE && intersects) {
//            if (focusedGui != null) {
//                component.setPressed(false);
////                EventProcessorUtils.release(mainComponent, component);
//
//                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
//                if (focusedGui == component) {
//                    processEvent(focusedGui, new MouseClickEvent(component, position, CLICK, event.button));
//                }
//
//                processEvent(focusedGui, new MouseClickEvent(component, position, RELEASE, event.button));
//                focusedGui.setPressed(false);
//            }
//        }
//    }
//
//    private void processEvent(Component component, MouseClickEvent mouseClickEvent) {
//        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
//        if (leguiEventProcessor != null) {
//            leguiEventProcessor.pushEvent(mouseClickEvent);
//        } else {
//            component.getListenerList().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
//        }
//    }
//
//    private boolean intersects(Component gui) {
//        boolean intersects;
//        LeguiIntersector intersector = gui.getIntersector();
//        if (intersector != null) {
//            intersects = intersector.intersects(gui, context.getCursorPosition());
//        } else {
//            intersects = Util.pointIntersectRect(Util.calculatePosition(gui), gui.getSize(), context.getCursorPosition());
//        }
//        return intersects;
//    }
//
//}

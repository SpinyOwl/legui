package org.liquidengine.legui.processor.system;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.system.MouseClickEvent;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Processor for mouse click events
 * Created by Alexander on 16.06.2016.
 */
public class GuiMouseClickEventProcessor extends LeguiSystemEventProcessor<MouseClickEvent> {

    public GuiMouseClickEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(MouseClickEvent event, Component mainGui, Component target) {
        context.getMouseButtonStates()[event.button] = event.action == GLFW_PRESS;
        Vector2f cursorPosition = context.getCursorPosition();
        context.getMouseButtonPressPosition()[event.button] = event.action == GLFW_PRESS ? new Vector2f(cursorPosition) : null;
        if (target != null) {
            process(event, target);
        }
    }


    private void pushUp(Component gui) {
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
//                        ComponentContainer container = (ComponentContainer) parent;
//                        container.remove(gui);
//                        container.add(gui);
//                        gui = container;
//                        parent = gui.getParent();
//                    }
//                } while (parent != null);
//            }
//        }
    }

    private void process(MouseClickEvent event, Component gui) {
//        Component focusedGui = processorState.getFocusedGui();
//        if (!gui.equals(focusedGui)) gui.setFocused(false);
//
//        processGui(event, gui);
//
//        updateGui(event, gui, focusedGui, intersects(gui));
    }

    private void updateGui(MouseClickEvent event, Component gui, Component focusedGui, boolean intersects) {
//        Vector2f cursorPosition = processorState.getCursorPosition();
//        if (event.action == GLFW_PRESS) {
//            if (intersects) {
//                pushUp(gui);
//                processorState.setFocusedGui(gui);
//                gui.setFocused(true);
//                gui.getListeners().getMouseActionListeners().forEach(listener -> listener.onPress(event.button, cursorPosition));
//            }
//        } else if (event.action == GLFW_RELEASE && intersects) {
//            if (focusedGui == null) return;
//            if (focusedGui.getIntersector().intersects(focusedGui, cursorPosition)) {
//                focusedGui.getListeners().getMouseActionListeners().forEach(listener -> listener.onClick(event.button, cursorPosition));
//            }
//            focusedGui.getListeners().getMouseActionListeners().forEach(listener -> listener.onRelease(event.button, cursorPosition));
//        }
    }

    private void processGui(MouseClickEvent event, Component gui) {
//        gui.getProcessors().getMouseClickEventProcessor().process(gui, event, processorState);
    }


    private boolean intersects(Component gui) {
//        boolean intersects;
//        Intersector intersector = gui.getIntersector();
//        if (intersector != null) {
//            intersects = intersector.intersects(gui, processorState.getCursorPosition());
//        } else {
//            intersects = Util.pointIntersectRect(Util.calculatePosition(gui), gui.getSize(), processorState.getCursorPosition());
//        }
//        return intersects;
        return false;
    }

}

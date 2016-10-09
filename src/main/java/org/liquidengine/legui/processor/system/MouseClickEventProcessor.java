package org.liquidengine.legui.processor.system;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.processor.EventProcessorUtils;
import org.liquidengine.legui.util.Util;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Processor for mouse click events
 * Created by Alexander on 16.06.2016.
 */
public class MouseClickEventProcessor extends SystemEventProcessor<SystemMouseClickEvent> {

    public MouseClickEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemMouseClickEvent event, Component mainGui) {
        Vector2f cursorPosition = context.getCursorPosition();
        context.getMouseButtonStates()[event.button] = event.action == GLFW_PRESS;
        context.getMouseButtonPressPosition()[event.button] = event.action == GLFW_PRESS ? new Vector2f(cursorPosition) : null;

        Component target = context.getMouseTargetGui();
        if (target != null) {
            process(event, mainGui, target);
        }
    }


    private void pushUp(Component gui) {
        Component parent = gui.getParent();
        if (parent != null) {
            boolean push = false;
            while (parent != null) {
                push = parent instanceof Widget;
                parent = parent.getParent();
                if (push) break;
            }

            if (push) {
                parent = gui.getParent();
                do {
                    if (parent instanceof ComponentContainer) {
                        ComponentContainer container = ((ComponentContainer) parent);
                        container.removeComponent(gui);
                        container.addComponent(gui);
                        gui = parent;
                        parent = gui.getParent();
                    }
                } while (parent != null);
            }
        }
    }

    private void process(SystemMouseClickEvent event, Component mainGui, Component gui) {
        Component focusedGui = context.getFocusedGui();
        if (!gui.equals(focusedGui)) gui.setFocused(false);


        gui.getProcessors().getMouseClickEventProcessor().process(gui, event, context);


        boolean intersects = intersects(gui);
        Vector2f cursorPosition = context.getCursorPosition();
        if (event.action == GLFW_PRESS) {
            if (intersects) {
                pushUp(gui);
                context.setFocusedGui(gui);
                gui.setFocused(true);
                gui.setPressed(true);
                EventProcessorUtils.release(mainGui, gui);

                Vector2f position = Util.calculatePosition(gui).sub(cursorPosition).negate();
                MouseClickEvent mouseClickEvent = new MouseClickEvent(gui, position, PRESS, event.button);

                gui.getListenerList().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
            } else {
                gui.setPressed(false);
            }
        } else if (event.action == GLFW_RELEASE && intersects) {
            if (focusedGui != null) {
                gui.setPressed(false);
                EventProcessorUtils.release(mainGui, gui);

                Vector2f position = Util.calculatePosition(gui).sub(cursorPosition).negate();
                if (focusedGui == gui) {
                    MouseClickEvent mouseClickEvent = new MouseClickEvent(gui, position, CLICK, event.button);
                    focusedGui.getListenerList().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
                }

                MouseClickEvent mouseClickEvent = new MouseClickEvent(gui, position, RELEASE, event.button);
                focusedGui.getListenerList().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
                focusedGui.setPressed(false);
            }
        }
    }

    private boolean intersects(Component gui) {
        boolean intersects;
        LeguiIntersector intersector = gui.getIntersector();
        if (intersector != null) {
            intersects = intersector.intersects(gui, context.getCursorPosition());
        } else {
            intersects = Util.pointIntersectRect(Util.calculatePosition(gui), gui.getSize(), context.getCursorPosition());
        }
        return intersects;
    }

}

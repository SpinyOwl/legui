package org.liquidengine.legui.listener.system.button;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.util.Util;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Shcherbin Alexander on 9/30/2016.
 */
public class ButtonSystemMouseClickEventListener implements SystemEventListener<Button, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, Button component, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (!component.equals(focusedGui)) component.getState().setFocused(false);

        boolean intersects = intersects(component, context);
        Vector2f cursorPosition = context.getCursorPosition();
        if (event.action == GLFW_PRESS) {
            if (intersects) {
                context.setFocusedGui(component);
                component.getState().setFocused(true);
                component.getState().setPressed(true);
                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
                processEvent(component, new MouseClickEvent(component, position, PRESS, event.button), context);
            } else {
                component.getState().setPressed(false);
            }
        } else if (event.action == GLFW_RELEASE && intersects) {
            if (focusedGui != null) {
                component.getState().setPressed(false);
                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
                if (focusedGui == component) {
                    processEvent(focusedGui, new MouseClickEvent(component, position, CLICK, event.button), context);
                }
                processEvent(focusedGui, new MouseClickEvent(component, position, RELEASE, event.button), context);
                focusedGui.getState().setPressed(false);
            }
        }

    }

    private void processEvent(Component component, MouseClickEvent mouseClickEvent, LeguiContext context) {
        LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) {
            leguiEventProcessor.pushEvent(mouseClickEvent);
        } else {
            component.getLeguiEventListeners().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
        }
    }

    private boolean intersects(Component gui, LeguiContext context) {
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

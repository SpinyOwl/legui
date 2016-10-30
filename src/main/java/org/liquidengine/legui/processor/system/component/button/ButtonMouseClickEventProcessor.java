package org.liquidengine.legui.processor.system.component.button;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.intersector.LeguiIntersector;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.MouseClickEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.EventProcessorUtils;
import org.liquidengine.legui.processor.LeguiEventListenerProcessor;
import org.liquidengine.legui.processor.system.component.LeguiComponentEventProcessor;
import org.liquidengine.legui.util.Util;

import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.PRESS;
import static org.liquidengine.legui.event.component.MouseClickEvent.MouseClickAction.RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Shcherbin Alexander on 9/30/2016.
 */
public class ButtonMouseClickEventProcessor implements SystemEventListener<Button, SystemMouseClickEvent> {
    @Override
    public void update(SystemMouseClickEvent event, Button component, LeguiContext context) {
        Component focusedGui = context.getFocusedGui();
        if (!component.equals(focusedGui)) component.setFocused(false);


//        component.getProcessors().getMouseClickEventProcessor().process(component, event, context);


        boolean intersects = intersects(component, context);
        Vector2f cursorPosition = context.getCursorPosition();
        if (event.action == GLFW_PRESS) {
            if (intersects) {
//                pushUp(component);
                context.setFocusedGui(component);
                component.setFocused(true);
                component.setPressed(true);
//                EventProcessorUtils.release(mainComponent, component);
                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
                processEvent(component, new MouseClickEvent(component, position, PRESS, event.button), context);
            } else {
                component.setPressed(false);
            }
        } else if (event.action == GLFW_RELEASE && intersects) {
            if (focusedGui != null) {
                component.setPressed(false);
//                EventProcessorUtils.release(mainComponent, component);

                Vector2f position = Util.calculatePosition(component).sub(cursorPosition).negate();
                if (focusedGui == component) {
                    processEvent(focusedGui, new MouseClickEvent(component, position, CLICK, event.button), context);
                }

                processEvent(focusedGui, new MouseClickEvent(component, position, RELEASE, event.button), context);
                focusedGui.setPressed(false);
            }
        }

    }

    private void processEvent(Component component, MouseClickEvent mouseClickEvent, LeguiContext context) {
        LeguiEventListenerProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (leguiEventProcessor != null) {
            leguiEventProcessor.pushEvent(mouseClickEvent);
        } else {
            component.getListenerList().getListeners(MouseClickEvent.class).forEach(listener -> listener.update(mouseClickEvent));
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

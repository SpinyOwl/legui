package org.liquidengine.legui.listener.system.def;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.CursorEnterEvent;
import org.liquidengine.legui.event.component.MouseDragEvent;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.LeguiEventListener;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * Created by Shcherbin Alexander on 10/31/2016.
 */
public class DefaultSystemCursorPosEventListener implements SystemEventListener<Component, SystemCursorPosEvent> {
    @Override
    public void update(SystemCursorPosEvent event, Component component, LeguiContext context) {
        if (component.isEnabled() && component.isVisible()) {
            process(event, component, context);
        }
    }

    private void process(SystemCursorPosEvent event, Component component, LeguiContext context) {
        updateComponentStatesAndCallListeners(event, component, context);
        if (component instanceof ComponentContainer) {
            processEventOnContainer(event, component, context);
        } else if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && context.getFocusedGui() == component) {
            component.getState().setPressed(true);
        }
    }

    private void processEventOnContainer(SystemCursorPosEvent event, Component component, LeguiContext context) {
        ComponentContainer container = ((ComponentContainer) component);
        List<Component>    all       = container.getComponents();
        for (Component child : all) {
            child.getSystemEventListeners().getListener(event.getClass()).update(event, child, context);
        }
    }

    /**
     * Updates standard context of frame element
     *
     * @param event
     * @param component
     * @param context
     */
    private void updateComponentStatesAndCallListeners(SystemCursorPosEvent event, Component component, LeguiContext context) {
        LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
        if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && component == context.getFocusedGui()) {
            List<LeguiEventListener<MouseDragEvent>> mouseDragEventListeners = component.getLeguiEventListeners().getListeners(MouseDragEvent.class);
            MouseDragEvent                           mouseDragEvent          = new MouseDragEvent(new Vector2f(event.fx, event.fy), context.getCursorPositionPrev(), component);
            if (leguiEventProcessor == null) {
                mouseDragEventListeners.forEach(l -> l.update(mouseDragEvent));
            } else {
                leguiEventProcessor.pushEvent(mouseDragEvent);
            }
        }

        List<LeguiEventListener<CursorEnterEvent>> listeners        = component.getLeguiEventListeners().getListeners(CursorEnterEvent.class);
        Vector2f                                   position         = Util.calculatePosition(component);
        Vector2f                                   cursorPosition   = context.getCursorPosition();
        boolean                                    intersects       = component.getIntersector().intersects(component, cursorPosition);
        Vector2f                                   mousePosition    = position.sub(cursorPosition).negate();
        boolean                                    update           = false;
        CursorEnterEvent                           cursorEnterEvent = null;
        if (component.getState().isHovered()) {
            if (!intersects || component != context.getMouseTargetGui()) {
                component.getState().setHovered(false);
                cursorEnterEvent = new CursorEnterEvent(component, CursorEnterEvent.CursorEnterAction.EXIT, mousePosition);
                update = true;
            }
        } else if (!component.getState().isHovered() && intersects && component == context.getMouseTargetGui()) {
            component.getState().setHovered(true);
            cursorEnterEvent = new CursorEnterEvent(component, CursorEnterEvent.CursorEnterAction.ENTER, mousePosition);
            update = true;
        }
        if (update) {
            if (leguiEventProcessor == null) {
                CursorEnterEvent finalCursorEnterEvent = cursorEnterEvent;
                listeners.forEach(listener -> listener.update(finalCursorEnterEvent));
            } else {
                leguiEventProcessor.pushEvent(cursorEnterEvent);
            }
        }
    }
}

package org.liquidengine.legui.processor.system;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.event.component.CursorEnterEvent;
import org.liquidengine.legui.event.component.MouseDragEvent;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.listener.component.LeguiListener;
import org.liquidengine.legui.processor.LeguiEventProcessor;
import org.liquidengine.legui.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * Event processor for cursor events. Updates GUI element depending on cursor position. Calls CursorEventListeners of GUI element
 * Created by Shcherbin Alexander on 6/16/2016.
 */
public class LeguiCursorPosEventProcessor extends LeguiSystemEventProcessor<SystemCursorPosEvent> {

    public LeguiCursorPosEventProcessor(LeguiContext context) {
        super(context);
    }

    @Override
    public void processEvent(SystemCursorPosEvent event, Component mainGui) {
        Component target = context.getMouseTargetGui();
        context.setCursorPositionPrev(context.getCursorPosition());
        context.setCursorPosition(new Vector2f(event.fx, event.fy));
        process(event, mainGui, target);
    }

    private void process(SystemCursorPosEvent event, Component gui, Component target) {
        gui.getProcessors().getCursorPosEventProcessor().process(gui, event, context);
        updateComponentStatesAndCallListeners(event, gui, target);
        if (gui instanceof ComponentContainer) {
            processEventOnContainer(event, gui, target);
        }
    }


    private void processEventOnContainer(SystemCursorPosEvent event, Component gui, Component target) {
        ComponentContainer container = ((ComponentContainer) gui);
        List<Component> all = container.getComponents();
        for (Component element : all) {
            process(event, element, target);
        }
    }

    /**
     * Updates standard context of gui element
     *
     * @param event
     * @param gui
     */
    private void updateComponentStatesAndCallListeners(SystemCursorPosEvent event, Component gui, Component target) {
        if (context.getMouseButtonStates()[GLFW.GLFW_MOUSE_BUTTON_LEFT] && gui == context.getFocusedGui()) {
            List<LeguiListener<MouseDragEvent>> mouseDragEventListeners = gui.getListenerList().getListeners(MouseDragEvent.class);
            MouseDragEvent mouseDragEvent = new MouseDragEvent(context.getCursorPosition(), context.getCursorPositionPrev(), gui);
            LeguiEventProcessor leguiEventProcessor = context.getLeguiEventProcessor();
            if (leguiEventProcessor == null) {
                mouseDragEventListeners.forEach(l -> l.update(mouseDragEvent));
            } else {
                leguiEventProcessor.pushEvent(mouseDragEvent);
            }
        }

        List<LeguiListener<CursorEnterEvent>> listeners = gui.getListenerList().getListeners(CursorEnterEvent.class);
        Vector2f position = Util.calculatePosition(gui);
        Vector2f cursorPosition = context.getCursorPosition();
        boolean intersects = gui.getIntersector().intersects(gui, cursorPosition);
        Vector2f mousePosition = position.sub(cursorPosition).negate();
        if (gui.isHovered()) {
            if (!intersects || gui != target) {
                gui.setHovered(false);
                listeners.forEach(listener -> listener.update(new CursorEnterEvent(gui, CursorEnterEvent.Action.EXIT, mousePosition)));
            }
        } else if (!gui.isHovered() && intersects && gui == target) {
            gui.setHovered(true);
            listeners.forEach(listener -> listener.update(new CursorEnterEvent(gui, CursorEnterEvent.Action.ENTER, mousePosition)));
        }
    }
}

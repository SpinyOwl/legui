package org.liquidengine.legui.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.ContainerHolder;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.context.LeguiEventQueue;
import org.liquidengine.legui.event.system.*;
import org.liquidengine.legui.processor.system.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiEventProcessor {

    private final Component mainGuiComponent;
    private final LeguiContext context;
    private final LeguiEventQueue leguiEventQueue;
    private final LeguiCallbackKeeper callbacks;
    private final Map<Class<? extends LeguiSystemEvent>, LeguiSystemEventProcessor> processorMap = new ConcurrentHashMap<>();

    public LeguiEventProcessor(Component mainGuiComponent, LeguiContext context) {
        this.mainGuiComponent = mainGuiComponent;
        this.context = context;
        callbacks = new LeguiCallbackKeeper(context.getTargetPointer());
        leguiEventQueue = new LeguiEventQueue(callbacks);
        initialize();
    }

    private void initialize() {
        LeguiCharModsEventProcessor guiCharModsEventProcessor = new LeguiCharModsEventProcessor(context);
        registerProcessor(CharModsEvent.class, guiCharModsEventProcessor);

        LeguiCursorEnterEventProcessor guiCursorEnterEventProcessor = new LeguiCursorEnterEventProcessor(context);
        registerProcessor(CursorEnterEvent.class, guiCursorEnterEventProcessor);

        LeguiCursorPosEventProcessor cursorPosEventProcessor = new LeguiCursorPosEventProcessor(context);
        registerProcessor(CursorPosEvent.class, cursorPosEventProcessor);

        LeguiMouseClickEventProcessor mouseClickEventProcessor = new LeguiMouseClickEventProcessor(context);
        registerProcessor(MouseClickEvent.class, mouseClickEventProcessor);

        LeguiCharEventProcessor charEventProcessor = new LeguiCharEventProcessor(context);
        registerProcessor(CharEvent.class, charEventProcessor);

        LeguiWindowSizeEventProcessor windowSizeEventProcessor = new LeguiWindowSizeEventProcessor(context);
        registerProcessor(WindowSizeEvent.class, windowSizeEventProcessor);

        LeguiKeyEventProcessor keyEventProcessor = new LeguiKeyEventProcessor(context);
        registerProcessor(KeyEvent.class, keyEventProcessor);

        LeguiDropEventCallback dropEventCallback = new LeguiDropEventCallback(context);
        registerProcessor(DropEvent.class, dropEventCallback);

        LeguiScrollEventProcessor scrollEventProcessor = new LeguiScrollEventProcessor(context);
        registerProcessor(ScrollEvent.class, scrollEventProcessor);
    }

    public LeguiContext getContext() {
        return context;
    }

    public LeguiCallbackKeeper getCallbacks() {
        return callbacks;
    }


    /**
     * Used to translate events to gui. Usually it used to pass event to main UI element (Main Panel).
     * <p>
     * If gui element is mainGuiComponent than it will be proceed as any other gui element and event will be passed
     * to all child element
     */
    public void processEvent() {
        LeguiSystemEvent event = leguiEventQueue.poll();
        if (event != null) {
            LeguiSystemEventProcessor concreteEventProcessor = processorMap.get(event.getClass());
            if (concreteEventProcessor != null) {
                context.setMouseTargetGui(getMouseTarget(null, mainGuiComponent, context.getCursorPosition()));
                concreteEventProcessor.processEvent(event, mainGuiComponent);
            }
        }
    }

    private Component getMouseTarget(Component target, Component component, Vector2f cursorPosition) {
        if (component instanceof ContainerHolder) {
            if (component.isVisible()) {
                ComponentContainer container = ((ContainerHolder) component).getContainer();
                if (component.getIntersector().intersects(component, cursorPosition)) {
                    target = component;
                    for (Component element : container.getComponents()) {
                        target = getMouseTarget(target, element, cursorPosition);
                    }
                }
            }
        } else {
            if (component.isVisible() && component.isEnabled() && component.getIntersector().intersects(component, cursorPosition)) {
                target = component;
            }
        }
        return target;
    }

    /**
     * Used to register processors for processing events and translate them to event processor
     *
     * @param eventClass
     * @param processor
     */
    public void registerProcessor(Class<? extends LeguiSystemEvent> eventClass, LeguiSystemEventProcessor processor) {
        processorMap.put(eventClass, processor);
    }

}

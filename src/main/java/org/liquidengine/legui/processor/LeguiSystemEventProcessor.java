package org.liquidengine.legui.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.context.LeguiSystemEventQueue;
import org.liquidengine.legui.event.system.*;
import org.liquidengine.legui.processor.system.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class LeguiSystemEventProcessor {

    private final Component mainGuiComponent;
    private final LeguiContext context;
    private final LeguiSystemEventQueue leguiEventQueue;
    private final LeguiCallbackKeeper callbackKeeper;
    private final Map<Class<? extends LeguiSystemEvent>, org.liquidengine.legui.processor.system.LeguiSystemEventProcessor> processorMap = new ConcurrentHashMap<>();

    public LeguiSystemEventProcessor(Component mainGuiComponent, LeguiContext context, LeguiCallbackKeeper callbackKeeper) {
        this.mainGuiComponent = mainGuiComponent;
        this.context = context;
        this.callbackKeeper = callbackKeeper;
        leguiEventQueue = new LeguiSystemEventQueue(callbackKeeper);
        initialize();
    }

    private void initialize() {
        LeguiCharModsEventProcessor guiCharModsEventProcessor = new LeguiCharModsEventProcessor(context);
        registerProcessor(SystemCharModsEvent.class, guiCharModsEventProcessor);

        LeguiCursorEnterEventProcessor guiCursorEnterEventProcessor = new LeguiCursorEnterEventProcessor(context);
        registerProcessor(SystemCursorEnterEvent.class, guiCursorEnterEventProcessor);

        LeguiCursorPosEventProcessor cursorPosEventProcessor = new LeguiCursorPosEventProcessor(context);
        registerProcessor(SystemCursorPosEvent.class, cursorPosEventProcessor);

        LeguiMouseClickEventProcessor mouseClickEventProcessor = new LeguiMouseClickEventProcessor(context);
        registerProcessor(SystemMouseClickEvent.class, mouseClickEventProcessor);

        LeguiCharEventProcessor charEventProcessor = new LeguiCharEventProcessor(context);
        registerProcessor(SystemCharEvent.class, charEventProcessor);

        LeguiWindowSizeEventProcessor windowSizeEventProcessor = new LeguiWindowSizeEventProcessor(context);
        registerProcessor(SystemWindowSizeEvent.class, windowSizeEventProcessor);

        LeguiKeyEventProcessor keyEventProcessor = new LeguiKeyEventProcessor(context);
        registerProcessor(SystemKeyEvent.class, keyEventProcessor);

        LeguiDropEventCallback dropEventCallback = new LeguiDropEventCallback(context);
        registerProcessor(SystemDropEvent.class, dropEventCallback);

        LeguiScrollEventProcessor scrollEventProcessor = new LeguiScrollEventProcessor(context);
        registerProcessor(SystemScrollEvent.class, scrollEventProcessor);
    }

    public LeguiContext getContext() {
        return context;
    }

    public LeguiCallbackKeeper getCallbackKeeper() {
        return callbackKeeper;
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
            org.liquidengine.legui.processor.system.LeguiSystemEventProcessor concreteEventProcessor = processorMap.get(event.getClass());
            if (concreteEventProcessor != null) {
                context.setMouseTargetGui(getMouseTarget(null, mainGuiComponent, context.getCursorPosition()));
                concreteEventProcessor.processEvent(event, mainGuiComponent);
            }
        }
    }

    private Component getMouseTarget(Component target, Component component, Vector2f cursorPosition) {
        if (component instanceof ComponentContainer) {
            if (component.isVisible()) {
                ComponentContainer container = ((ComponentContainer) component);
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
    public void registerProcessor(Class<? extends LeguiSystemEvent> eventClass, org.liquidengine.legui.processor.system.LeguiSystemEventProcessor processor) {
        processorMap.put(eventClass, processor);
    }

}

package org.liquidengine.legui.processor;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.context.LeguiSystemEventQueue;
import org.liquidengine.legui.event.SystemEvent;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class SystemEventListenerProcessor {

    private final Component mainGuiComponent;
    private final LeguiContext context;
    private final LeguiSystemEventQueue leguiEventQueue;
    private final LeguiCallbackKeeper callbackKeeper;
//    private final Map<Class<? extends SystemEvent>, org.liquidengine.legui.processor.system.SystemEventProcessor> processorMap = new ConcurrentHashMap<>();

    public SystemEventListenerProcessor(Component mainGuiComponent, LeguiContext context, LeguiCallbackKeeper callbackKeeper) {
        this.mainGuiComponent = mainGuiComponent;
        this.context = context;
        this.callbackKeeper = callbackKeeper;
        leguiEventQueue = new LeguiSystemEventQueue(callbackKeeper);
        initialize();
    }

    private void initialize() {
//        CharModsEventProcessor guiCharModsEventProcessor = new CharModsEventProcessor(context);
//        registerProcessor(SystemCharModsEvent.class, guiCharModsEventProcessor);
//
//        CursorEnterEventProcessor guiCursorEnterEventProcessor = new CursorEnterEventProcessor(context);
//        registerProcessor(SystemCursorEnterEvent.class, guiCursorEnterEventProcessor);
//
//        CursorPosEventProcessor cursorPosEventProcessor = new CursorPosEventProcessor(context);
//        registerProcessor(SystemCursorPosEvent.class, cursorPosEventProcessor);
//
//        MouseClickEventProcessor mouseClickEventProcessor = new MouseClickEventProcessor(context);
//        registerProcessor(SystemMouseClickEvent.class, mouseClickEventProcessor);
//
//        CharEventProcessor charEventProcessor = new CharEventProcessor(context);
//        registerProcessor(SystemCharEvent.class, charEventProcessor);
//
//        WindowSizeEventProcessor windowSizeEventProcessor = new WindowSizeEventProcessor(context);
//        registerProcessor(SystemWindowSizeEvent.class, windowSizeEventProcessor);
//
//        KeyEventProcessor keyEventProcessor = new KeyEventProcessor(context);
//        registerProcessor(SystemKeyEvent.class, keyEventProcessor);
//
//        DropEventCallback dropEventCallback = new DropEventCallback(context);
//        registerProcessor(SystemDropEvent.class, dropEventCallback);
//
//        ScrollEventProcessor scrollEventProcessor = new ScrollEventProcessor(context);
//        registerProcessor(SystemScrollEvent.class, scrollEventProcessor);
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
        SystemEvent event = leguiEventQueue.poll();
        if (event != null) {
            mainGuiComponent.getProcessors().getListener(event.getClass()).update(event, mainGuiComponent, context);
//            org.liquidengine.legui.processor.system.SystemEventProcessor concreteEventProcessor = processorMap.get(event.getClass());
//            if (concreteEventProcessor != null) {
//                context.setMouseTargetGui(getMouseTarget(null, mainGuiComponent, context.getCursorPosition()));
//                concreteEventProcessor.processEvent(event, mainGuiComponent);
//            }
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

//    /**
//     * Used to register processors for processing events and translate them to event processor
//     *
//     * @param eventClass
//     * @param processor
//     */
//    public void registerProcessor(Class<? extends SystemEvent> eventClass, org.liquidengine.legui.processor.system.SystemEventProcessor processor) {
//        processorMap.put(eventClass, processor);
//    }

}

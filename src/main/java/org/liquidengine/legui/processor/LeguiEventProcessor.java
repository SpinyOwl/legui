package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Component;
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
    }

    private void initialize() {
        GuiCharModsEventProcessor guiCharModsEventProcessor = new GuiCharModsEventProcessor(context);
        registerProcessor(CharModsEvent.class, guiCharModsEventProcessor);

        GuiCursorEnterEventProcessor guiCursorEnterEventProcessor = new GuiCursorEnterEventProcessor(context);
        registerProcessor(CursorEnterEvent.class, guiCursorEnterEventProcessor);

        GuiCursorPosEventProcessor cursorPosEventProcessor = new GuiCursorPosEventProcessor(context);
        registerProcessor(CursorPosEvent.class, cursorPosEventProcessor);

        GuiMouseClickEventProcessor mouseClickEventProcessor = new GuiMouseClickEventProcessor(context);
        registerProcessor(MouseClickEvent.class, mouseClickEventProcessor);

        GuiCharEventProcessor charEventProcessor = new GuiCharEventProcessor(context);
        registerProcessor(CharEvent.class, charEventProcessor);

        GuiWindowSizeEventProcessor windowSizeEventProcessor = new GuiWindowSizeEventProcessor(context);
        registerProcessor(WindowSizeEvent.class, windowSizeEventProcessor);

        GuiKeyEventProcessor keyEventProcessor = new GuiKeyEventProcessor(context);
        registerProcessor(KeyEvent.class, keyEventProcessor);

        GuiDropEventCallback dropEventCallback = new GuiDropEventCallback(context);
        registerProcessor(DropEvent.class, dropEventCallback);

        GuiScrollEventProcessor scrollEventProcessor = new GuiScrollEventProcessor(context);
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
        if (event == null) return;

        LeguiSystemEventProcessor concreteEventProcessor = processorMap.get(event.getClass());
        if (concreteEventProcessor == null) return;
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

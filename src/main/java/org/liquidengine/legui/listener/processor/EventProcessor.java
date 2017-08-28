package org.liquidengine.legui.listener.processor;

import org.liquidengine.legui.event.Event;

/**
 * @author Aliaksandr_Shcherbin.
 */
public abstract class EventProcessor {
    private static EventProcessor INSTANCE = new EventProcessorImpl();

    public static EventProcessor getInstance() {
        return INSTANCE;
    }

    public static void setInstance(EventProcessor eventProcessor) {
        INSTANCE = eventProcessor;
    }

    public abstract void processEvents();

    public abstract void pushEvent(Event event);
}

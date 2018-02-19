package org.liquidengine.legui.listener.processor;

import org.liquidengine.legui.event.Event;

/**
 * UI Events processor interface.
 *
 * @author ShchAlexander.
 */
public abstract class EventProcessor {

    /**
     * Instance of event processor.
     */
    private static EventProcessor INSTANCE = new EventProcessorImpl();

    /**
     * Returns instance of event processor.
     *
     * @return instance of event processor.
     */
    public static EventProcessor getInstance() {
        return INSTANCE;
    }

    /**
     * Used to set event processor.
     *
     * @param eventProcessor event processor to set.
     */
    public static void setInstance(EventProcessor eventProcessor) {
        INSTANCE = eventProcessor;
    }

    /**
     * Should be called to process events.
     */
    public abstract void processEvents();

    /**
     * Used to push event to event processor.
     *
     * @param event event to push to event processor.
     */
    public abstract void pushEvent(Event event);
}

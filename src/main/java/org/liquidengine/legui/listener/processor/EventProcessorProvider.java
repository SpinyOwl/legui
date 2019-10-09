package org.liquidengine.legui.listener.processor;

/**
 * UI Events processor provider.
 *
 * @author ShchAlexander.
 */
public final class EventProcessorProvider {

    //@formatter:off

    /**
     * Hidden constructor.
     */
    private EventProcessorProvider() {
    }

    /**
     * Returns instance of event processor.
     *
     * @return instance of event processor.
     */
    public static EventProcessor getInstance() {
        return EventProcessorProviderHolder.INSTANCE;
    }

    /**
     * Used to set event processor.
     *
     * @param eventProcessor event processor to set.
     */
    public static void setInstance(EventProcessor eventProcessor) {
        if (eventProcessor != null) {
            EventProcessorProviderHolder.INSTANCE = eventProcessor;
        }
    }

    /**
     * Instance holder.
     */
    private static class EventProcessorProviderHolder {
        private static EventProcessor INSTANCE = new EventProcessorImpl();
    }
    //@formatter:on

}

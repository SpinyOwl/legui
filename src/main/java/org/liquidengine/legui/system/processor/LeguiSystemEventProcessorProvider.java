package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.system.event.LeguiSystemEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class LeguiSystemEventProcessorProvider {
    private Map<Class<? extends LeguiSystemEvent>, LeguiSystemEventProcessor<? extends LeguiSystemEvent>> processorMap = new ConcurrentHashMap<>();

    private LeguiSystemEventProcessorProvider() {
    }

    public static LeguiSystemEventProcessorProvider getInstance() {
        return LSEPPH.I;
    }

    public <E extends LeguiSystemEvent> void registerProcessor(Class<E> eventClass, LeguiSystemEventProcessor<E> processor) {
        processorMap.put(eventClass, processor);
    }

    public <E extends LeguiSystemEvent> LeguiSystemEventProcessor<E> getProcessor(Class<E> eventClass) {
        return (LeguiSystemEventProcessor<E>) processorMap.get(eventClass);
    }

    private static class LSEPPH {
        private static final LeguiSystemEventProcessorProvider I = new LeguiSystemEventProcessorProvider();
    }
}

package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.system.event.SystemEvent;
import org.liquidengine.legui.system.event.SystemWindowSizeEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class SystemEventProcessorProvider {
    private Map<Class<? extends SystemEvent>, SystemEventProcessor<? extends SystemEvent>> processorMap = new ConcurrentHashMap<>();

    private SystemEventProcessorProvider() {
        registerProcessor(SystemWindowSizeEvent.class, new WindowSizeEventProcessor());
    }

    public static SystemEventProcessorProvider getInstance() {
        return LSEPPH.I;
    }

    public <E extends SystemEvent> void registerProcessor(Class<E> eventClass, SystemEventProcessor<E> processor) {
        processorMap.put(eventClass, processor);
    }

    public <E extends SystemEvent> SystemEventProcessor<E> getProcessor(Class<E> eventClass) {
        return (SystemEventProcessor<E>) processorMap.get(eventClass);
    }

    private static class LSEPPH {
        private static final SystemEventProcessorProvider I = new SystemEventProcessorProvider();
    }
}

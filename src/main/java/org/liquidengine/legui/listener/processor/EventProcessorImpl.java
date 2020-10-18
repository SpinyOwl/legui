package org.liquidengine.legui.listener.processor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.listener.EventListener;

/**
 * Default implementation of event processor.
 * <p>
 * Created by ShchAlexander on 1/25/2017.
 */
public class EventProcessorImpl implements EventProcessor {

    private static final Logger LOGGER = LogManager.getLogger();

    private Queue<Event> first = new ConcurrentLinkedQueue<>();
    private Queue<Event> second = new ConcurrentLinkedQueue<>();


    /**
     * Should be called to process events.
     */
    @Override
    public void processEvents() {
        swap();

        for (Event event = second.poll(); event != null; event = second.poll()) {
            Component targetComponent = event.getTargetComponent();
            if (targetComponent == null) {
                return;
            }
            List<? extends EventListener> listeners = targetComponent.getListenerMap().getListeners(event.getClass());
            for (EventListener listener : listeners) {
                listener.process(event);
            }
        }
    }

    private void swap() {
        Queue<Event> temp = first;
        first = second;
        second = temp;
    }

    /**
     * Used to push event to event processor.
     *
     * @param event event to push to event processor.
     */
    @Override
    public void pushEvent(Event event) {
        if (event.getContext() != null && event.getContext().isDebugEnabled()) {
            LOGGER.debug(event);
        }
        first.add(event);
    }

    /**
     * Returns true if there are events that should be processed.
     *
     * @return true if there are events that should be processed.
     */
    @Override
    public boolean hasEvents() {
        return !(first.isEmpty() && second.isEmpty());
    }
}

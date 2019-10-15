package org.liquidengine.legui.system.handler.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemEvent;
import org.liquidengine.legui.system.handler.SystemEventHandler;
import org.liquidengine.legui.system.handler.SystemEventHandlerProvider;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public class SystemEventProcessorImpl implements SystemEventProcessor {

    private Queue<SystemEvent> eventQueue = new ConcurrentLinkedQueue<>();

    /**
     * Process events.
     *
     * @param frame   the frame
     * @param context the context
     */
    public void processEvents(Frame frame, Context context) {
        for (SystemEvent event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            SystemEventHandler processor = SystemEventHandlerProvider.getInstance().getProcessor(event.getClass());
            if (processor != null) {
                processor.handle(event, frame, context);
            }
        }
    }

    /**
     * Push event.
     *
     * @param event the event
     */
    public void pushEvent(SystemEvent event) {
        eventQueue.add(event);
    }

    /**
     * Returns true if there are events that should be processed.
     *
     * @return true if there are events that should be processed.
     */
    @Override
    public boolean hasEvents() {
        return !eventQueue.isEmpty();
    }
}

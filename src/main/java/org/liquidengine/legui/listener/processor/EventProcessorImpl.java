package org.liquidengine.legui.listener.processor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.Event;
import org.liquidengine.legui.event.WindowCloseEvent;
import org.liquidengine.legui.listener.EventListener;
import org.liquidengine.legui.system.LeguiSystem;
import org.liquidengine.legui.system.Window;

/**
 * Default implementation of event processor.
 * <p>
 * Created by ShchAlexander on 1/25/2017.
 */
public class EventProcessorImpl extends EventProcessor {

    private Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();


    /**
     * Should be called to process events.
     */
    @Override
    public void processEvents() {
        for (Event event = eventQueue.poll(); event != null; event = eventQueue.poll()) {
            Component targetComponent = event.getTargetComponent();
            if (targetComponent == null) {
                return;
            }
            List<? extends EventListener> listeners = targetComponent.getListenerMap().getListeners(event.getClass());
            for (EventListener listener : listeners) {
                listener.process(event);
            }

            if (event instanceof WindowCloseEvent) {
                long glfwWindow = event.getContext().getGlfwWindow();
                Window window = LeguiSystem.getWindow(glfwWindow);
                for (EventListener<WindowCloseEvent> listener : window.getWindowCloseEventListeners()) {
                    listener.process((WindowCloseEvent) event);
                }
            }
        }
    }

    /**
     * Used to push event to event processor.
     *
     * @param event event to push to event processor.
     */
    @Override
    public void pushEvent(Event event) {
        eventQueue.add(event);
    }
}

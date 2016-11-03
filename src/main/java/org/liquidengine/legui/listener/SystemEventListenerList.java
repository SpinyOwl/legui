package org.liquidengine.legui.listener;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.processor.SystemEventListenerProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public class SystemEventListenerList<C extends Component> {

    private final Lock lock = new ReentrantLock();
    private final Map<Class<? extends SystemEvent>, SystemEventListener<C, ? extends SystemEvent>> listeners = new ConcurrentHashMap<>();
    private Class<C> componentClass;

    public SystemEventListenerList(Class<C> componentClass) {
        this.componentClass = componentClass;
    }

    public <E extends SystemEvent> void setListener(Class<E> eventClass, SystemEventListener<C, E> listener) {
        listeners.put(eventClass, listener);
    }

    public <E extends SystemEvent> void removeListener(Class<E> eventClass) {
        listeners.remove(eventClass);
    }

    public <E extends SystemEvent> SystemEventListener<C, E> getListener(Class<E> eventClass) {
        SystemEventListener listener = listeners.get(eventClass);
        if (listener == null) {
            listeners.put(eventClass, listener = SystemEventListenerProvider.getProvider().getListener(componentClass, eventClass));
        }
        return listener;
    }
}

package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.AbstractEvent;
import org.liquidengine.legui.event.MouseDragEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class ListenerMap {
    private final Lock                                                               lock        = new ReentrantLock();
    private       Map<Class<? extends AbstractEvent>, List<? extends EventListener>> listenerMap = new ConcurrentHashMap<>();

    public <T extends AbstractEvent> void addListener(Class<T> eventClass, EventListener<T> listener) {
        getListeners(eventClass).add(listener);
    }

    public <E extends AbstractEvent> List<EventListener<E>> getListeners(Class<E> eventClass) {
        lock.lock();
        List<EventListener<E>> eventListeners = (List<EventListener<E>>) listenerMap.get(eventClass);
        if (eventListeners == null) listenerMap.put(eventClass, eventListeners = new CopyOnWriteArrayList<>());
        lock.unlock();
        return eventListeners;
    }

    public <T extends AbstractEvent>void removeListener(Class<T> eventClass, EventListener<T> listener) {
        getListeners(eventClass).remove(listener);
    }
}

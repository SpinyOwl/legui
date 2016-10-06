package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.AbstractComponentEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class LeguiListenerList {

    private final Lock lock = new ReentrantLock();
    private final Map<Class<? extends AbstractComponentEvent>, List<? extends IEventListener>> listeners = new ConcurrentHashMap<>();

    public LeguiListenerList() {
    }

    public <T extends AbstractComponentEvent> void addListener(Class<T> eventClass, IEventListener<T> listener) {
        getListeners(eventClass).add(listener);
    }

    public <T extends AbstractComponentEvent> void removeListener(Class<T> eventClass, IEventListener<T> listener) {
        getListeners(eventClass).remove(listener);
    }

    public <T extends AbstractComponentEvent> List<IEventListener<T>> getListeners(Class<T> eventClass) {
        lock.lock();
        List<IEventListener<T>> IEventListeners = (List<IEventListener<T>>) listeners.get(eventClass);
        if (IEventListeners == null) listeners.put(eventClass, IEventListeners = new CopyOnWriteArrayList<>());
        lock.unlock();
        return IEventListeners;
    }

}

package org.liquidengine.legui.listener.component;

import org.liquidengine.legui.event.component.LeguiComponentEvent;

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
    private final Map<Class<? extends LeguiComponentEvent>, List<? extends LeguiListener>> listeners = new ConcurrentHashMap<>();

    public LeguiListenerList() {
    }

    public <T extends LeguiComponentEvent> void addListener(Class<T> eventClass, LeguiListener<T> listener) {
        getListeners(eventClass).add(listener);
    }

    public <T extends LeguiComponentEvent> void removeListener(Class<T> eventClass, LeguiListener<T> listener) {
        getListeners(eventClass).remove(listener);
    }

    public <T extends LeguiComponentEvent> List<LeguiListener<T>> getListeners(Class<T> eventClass) {
        lock.lock();
        List<LeguiListener<T>> leguiListeners = (List<LeguiListener<T>>) listeners.get(eventClass);
        if (leguiListeners == null) listeners.put(eventClass, leguiListeners = new CopyOnWriteArrayList<>());
        lock.unlock();
        return leguiListeners;
    }

}

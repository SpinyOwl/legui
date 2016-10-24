package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiEvent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Shcherbin Alexander on 9/29/2016.
 */
public class LeguiEventListenerList implements Serializable{

    private final Lock lock = new ReentrantLock();
    private final Map<Class<? extends LeguiEvent>, List<? extends LeguiEventListener>> listeners = new ConcurrentHashMap<>();

    public LeguiEventListenerList() {
    }

    public <T extends LeguiEvent> void addListener(Class<T> eventClass, LeguiEventListener<T> listener) {
        getListeners(eventClass).add(listener);
    }

    public <T extends LeguiEvent> void removeListener(Class<T> eventClass, LeguiEventListener<T> listener) {
        getListeners(eventClass).remove(listener);
    }

    public <T extends LeguiEvent> List<LeguiEventListener<T>> getListeners(Class<T> eventClass) {
        lock.lock();
        List<LeguiEventListener<T>> LeguiEventListeners = (List<LeguiEventListener<T>>) listeners.get(eventClass);
        if (LeguiEventListeners == null) listeners.put(eventClass, LeguiEventListeners = new CopyOnWriteArrayList<>());
        lock.unlock();
        return LeguiEventListeners;
    }

}

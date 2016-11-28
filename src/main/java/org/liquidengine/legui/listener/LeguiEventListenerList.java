package org.liquidengine.legui.listener;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
public class LeguiEventListenerList implements Serializable {

    private final Lock lock = new ReentrantLock();
    private Map<Class<? extends LeguiEvent>, List<? extends LeguiEventListener>> listeners = new ConcurrentHashMap<>();

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

    public Map<Class<? extends LeguiEvent>, List<? extends LeguiEventListener>> getListeners() {
        return listeners;
    }

    public void setListeners(Map<Class<? extends LeguiEvent>, List<? extends LeguiEventListener>> listeners) {
        this.listeners = listeners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LeguiEventListenerList that = (LeguiEventListenerList) o;

        return new EqualsBuilder()
                .append(listeners, that.listeners)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(listeners)
                .toHashCode();
    }
}

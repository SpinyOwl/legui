package org.liquidengine.legui.listener;

import org.liquidengine.legui.event.LeguiEvent;

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
    private final Lock                                                                 lock        = new ReentrantLock();
    private       Map<Class<? extends LeguiEvent>, List<? extends LeguiEventListener>> listenerMap = new ConcurrentHashMap<>();

    public void addListener() {
    }

    public <E extends LeguiEvent> List<LeguiEventListener<E>> getListeners(Class<E> eventClass) {
        lock.lock();
        List<LeguiEventListener<E>> LeguiEventListeners = (List<LeguiEventListener<E>>) listenerMap.get(eventClass);
        if (LeguiEventListeners == null) listenerMap.put(eventClass, LeguiEventListeners = new CopyOnWriteArrayList<>());
        lock.unlock();
        return LeguiEventListeners;
    }
}

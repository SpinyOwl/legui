package org.liquidengine.legui.listener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.liquidengine.legui.event.Event;

/**
 * Used to hold event listeners.
 */
public class ListenerMap {

    private final Lock lock = new ReentrantLock();
    private Map<Class<? extends Event>, List<? extends EventListener>> listenerMap = new ConcurrentHashMap<>();

    /**
     * Used to add event listener for specified event type.
     *
     * @param eventClass event class.
     * @param listener listener to add for specified event.
     * @param <E> event type.
     */
    public <E extends Event> void addListener(Class<E> eventClass, EventListener<E> listener) {
        getListeners(eventClass).add(listener);
    }

    /**
     * Returns event listeners for specified event type.
     *
     * @param eventClass event class for which registered listeners.
     * @param <E> event type.
     *
     * @return event listeners for specified event type.
     */
    public <E extends Event> List<EventListener<E>> getListeners(Class<E> eventClass) {
        lock.lock();
        List<EventListener<E>> eventListeners = (List<EventListener<E>>) listenerMap.get(eventClass);
        if (eventListeners == null) {
            listenerMap.put(eventClass, eventListeners = new CopyOnWriteArrayList<>());
        }
        lock.unlock();
        return eventListeners;
    }

    /**
     * Used to remove specified event listener.
     *
     * @param eventClass event class.
     * @param listener listener to remove.
     * @param <E> event type.
     */
    public <E extends Event> void removeListener(Class<E> eventClass, EventListener<E> listener) {
        getListeners(eventClass).remove(listener);
    }

    /**
     * Used to remove all listeners for specified event type.
     * @param eventClass event class.
     * @param <E> event type.
     */
    public <E extends Event> void removeAllListeners(Class<E> eventClass) {
        listenerMap.remove(eventClass);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(listenerMap)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ListenerMap that = (ListenerMap) obj;

        return new EqualsBuilder()
            .append(listenerMap, that.listenerMap)
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("listenerMap", listenerMap)
            .toString();
    }
}

package org.liquidengine.legui.processor;

import com.google.common.base.Objects;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.event.system.SystemCharEvent;
import org.liquidengine.legui.listener.SystemEventListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public class DefaultSystemEventListenerProvider extends SystemEventListenerProvider {
    //@formatter:off
    private static final SystemEventListener<Component, SystemEvent> EMPTY_EVENT_LISTENER = (event, component, context) -> {
        System.out.println(event);
    };
    //@formatter:on
    private Map<Class<? extends SystemEvent>, SystemEventListener> defaultListenerMap = new ConcurrentHashMap<>();
    private Map<ComponentEventKey, SystemEventListener> listenerMap = new ConcurrentHashMap<>();

    public DefaultSystemEventListenerProvider() {
        initializeDefaults();
        initializeListeners();
    }

    private void initializeDefaults() {
        registerDefaultListener(SystemCharEvent.class, ((event, component, context) -> System.out.println(component + " " + Character.toChars(event.codepoint))));
    }

    private void initializeListeners() {

    }

    public <C extends Component, E extends SystemEvent> void registerListener(Class<C> componentClass, Class<E> eventClass, SystemEventListener<C, E> listener) {
        listenerMap.put(new ComponentEventKey(componentClass, eventClass), listener);
    }

    public <E extends SystemEvent> void registerDefaultListener(Class<E> eventClass, SystemEventListener<?, E> listener) {
        defaultListenerMap.put(eventClass, listener);
    }

    @Override
    public <C extends Component, E extends SystemEvent> SystemEventListener getListener(Class<C> componentClass, Class<E> eventcClass) {
        return listenerMap.getOrDefault(new ComponentEventKey<>(componentClass, eventcClass),
                defaultListenerMap.getOrDefault(eventcClass, EMPTY_EVENT_LISTENER));
    }

    private static class ComponentEventKey<C extends Component, E extends SystemEvent> {
        private final Class<C> componentClass;
        private final Class<E> eventClass;

        public ComponentEventKey(Class<C> componentClass, Class<E> eventClass) {
            this.componentClass = componentClass;
            this.eventClass = eventClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ComponentEventKey)) return false;
            ComponentEventKey<?, ?> that = (ComponentEventKey<?, ?>) o;
            return Objects.equal(componentClass, that.componentClass) &&
                    Objects.equal(eventClass, that.eventClass);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(componentClass, eventClass);
        }
    }


}

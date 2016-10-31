package org.liquidengine.legui.processor;

import com.google.common.base.Objects;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.event.system.SystemCursorPosEvent;
import org.liquidengine.legui.event.system.SystemMouseClickEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.processor.post.SystemMouseClickEventPostprocessor;
import org.liquidengine.legui.processor.pre.SystemCursorPosEventPreprocessor;
import org.liquidengine.legui.processor.pre.SystemMouseClickEventPreprocessor;
import org.liquidengine.legui.processor.system.component.button.ButtonCursorPosEventProcessor;
import org.liquidengine.legui.processor.system.component.button.ButtonMouseClickEventListener;
import org.liquidengine.legui.processor.system.component.checkbox.CheckBoxMouseClickListener;
import org.liquidengine.legui.processor.system.component.container.ContainerSystemMouseClickEventListener;
import org.liquidengine.legui.processor.system.component.def.DefaultSystemCursorPosEventListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public class DefaultSystemEventListenerProvider extends SystemEventListenerProvider {
    //@formatter:off
    private static final SystemEventListener<Component, SystemEvent> EMPTY_EVENT_LISTENER = (event, component, context) -> {};
    //@formatter:on
    private Map<Class<? extends SystemEvent>, SystemEventListener> defaultListenerMap = new ConcurrentHashMap<>();
    private Map<ComponentEventKey, SystemEventListener> listenerMap = new ConcurrentHashMap<>();
    private Map<Class<? extends SystemEvent>, SystemEventPostprocessor> postprocessorMap = new ConcurrentHashMap<>();
    private Map<Class<? extends SystemEvent>, SystemEventPreprocessor> preprocessorMap = new ConcurrentHashMap<>();

    public DefaultSystemEventListenerProvider() {
        initializePreprocessors();
        initializePostprocessors();
        initializeDefaults();
        initializeListeners();
    }

    private void initializePreprocessors() {
        registerPreprocessor(SystemCursorPosEvent.class, new SystemCursorPosEventPreprocessor());
        registerPreprocessor(SystemMouseClickEvent.class, new SystemMouseClickEventPreprocessor());
    }

    private void initializePostprocessors() {
        registerPostprocessor(SystemMouseClickEvent.class, new SystemMouseClickEventPostprocessor());
    }

    private void initializeDefaults() {
//        registerDefaultListener(SystemCharEvent.class, ((event, component, context) -> System.out.println(component + " event: " + event + " cp: " + Character.toChars(event.codepoint))));
//        registerDefaultListener(SystemMouseClickEvent.class, new DefaultSystemMouseClickEventListener());
    }

    private void initializeListeners() {
        registerListener(ComponentContainer.class, SystemMouseClickEvent.class, new ContainerSystemMouseClickEventListener());
        registerListener(Component.class, SystemCursorPosEvent.class, new DefaultSystemCursorPosEventListener());
        registerListener(Button.class, SystemMouseClickEvent.class, new ButtonMouseClickEventListener());
        registerListener(Button.class, SystemCursorPosEvent.class, new ButtonCursorPosEventProcessor());
        registerListener(CheckBox.class, SystemMouseClickEvent.class, new CheckBoxMouseClickListener());
    }

    public <C extends Component, E extends SystemEvent> void registerListener(Class<C> componentClass, Class<E> eventClass, SystemEventListener<C, E> listener) {
        if (listener != null && eventClass != null && componentClass != null) {
            listenerMap.put(new ComponentEventKey(componentClass, eventClass), listener);
        }
    }

    public <E extends SystemEvent> void registerDefaultListener(Class<E> eventClass, SystemEventListener<?, E> listener) {
        if (listener != null && eventClass != null) {
            defaultListenerMap.put(eventClass, listener);
        }
    }

    @Override
    public <C extends Component, E extends SystemEvent> SystemEventListener getListener(Class<C> componentClass, Class<E> eventClass) {
        return cycledSearchOfListener(componentClass, eventClass, defaultListenerMap.getOrDefault(eventClass, EMPTY_EVENT_LISTENER));
    }

    @Override
    public <E extends SystemEvent> SystemEventPreprocessor getPreprocessor(Class<E> eventClass) {
        return preprocessorMap.get(eventClass);
    }

    public <E extends SystemEvent, P extends SystemEventPreprocessor> void registerPreprocessor(Class<E> eventClass, P preprocessor) {
        if (preprocessor != null) {
            preprocessorMap.put(eventClass, preprocessor);
        }
    }

    @Override
    public <E extends SystemEvent> SystemEventPostprocessor getPostprocessor(Class<E> eventClass) {
        return postprocessorMap.get(eventClass);
    }

    public <E extends SystemEvent, P extends SystemEventPostprocessor> void registerPostprocessor(Class<E> eventClass, P postprocessor) {
        if (postprocessor != null) {
            postprocessorMap.put(eventClass, postprocessor);
        }
    }

    private <C extends Component, E extends SystemEvent> SystemEventListener
    cycledSearchOfListener(Class<C> componentClass, Class<E> eventClass, SystemEventListener defaultListener) {
        SystemEventListener listener = null;
        Class cClass = componentClass;
        while (listener == null) {
            listener = listenerMap.get(new ComponentEventKey<>(cClass, eventClass));
            if (cClass.isAssignableFrom(Component.class)) break;
            cClass = cClass.getSuperclass();
        }
        if (listener == null) listener = defaultListener;
        return listener;
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

package org.liquidengine.legui.listener;

import com.google.common.base.Objects;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.event.system.*;
import org.liquidengine.legui.listener.system.def.*;
import org.liquidengine.legui.listener.system.radiobutton.RadioButtonSystemMouseClickListener;
import org.liquidengine.legui.listener.system.scrollbar.ScrollBarSystemCursorPosEventListener;
import org.liquidengine.legui.listener.system.scrollbar.ScrollBarSystemMouseClickEventListener;
import org.liquidengine.legui.listener.system.scrollbar.ScrollBarSystemScrollEventListener;
import org.liquidengine.legui.listener.system.slider.SliderSystemCursorPosEventListener;
import org.liquidengine.legui.listener.system.slider.SliderSystemMouseClickEventListener;
import org.liquidengine.legui.listener.system.slider.SliderSystemScrollEventListener;
import org.liquidengine.legui.listener.system.textarea.TextAreaSystemCharEventListener;
import org.liquidengine.legui.listener.system.textarea.TextAreaSystemCursorPosEventListener;
import org.liquidengine.legui.listener.system.textarea.TextAreaSystemKeyEventListener;
import org.liquidengine.legui.listener.system.textarea.TextAreaSystemMouseClickEventListener;
import org.liquidengine.legui.listener.system.textinput.TextInputSystemCharEventListener;
import org.liquidengine.legui.listener.system.textinput.TextInputSystemCursorPosEventListener;
import org.liquidengine.legui.listener.system.textinput.TextInputSystemKeyEventProcessor;
import org.liquidengine.legui.listener.system.textinput.TextInputSystemMouseClickEventListener;
import org.liquidengine.legui.listener.system.toggleButton.ToggleButtonMouseClickEventProcessor;
import org.liquidengine.legui.processor.SystemEventPostprocessor;
import org.liquidengine.legui.processor.SystemEventPreprocessor;
import org.liquidengine.legui.processor.post.SystemMouseClickEventPostprocessor;
import org.liquidengine.legui.processor.pre.SystemCursorPosEventPreprocessor;
import org.liquidengine.legui.processor.pre.SystemMouseClickEventPreprocessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import org.liquidengine.legui.listener.system.container.ContainerSystemMouseClickEventListener;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public class DefaultSystemEventListenerProvider extends SystemEventListenerProvider {
    private static final SystemEventListener<Component, SystemEvent>                 DEFAULT_EVENT_LISTENER       = (event, component, context) -> {
        Component child = component.getComponentAt(context.getCursorPosition());
        if (child != component && child != null) {
            child.getSystemEventListeners().getListener(event.getClass()).update(event, child, context);
        }
    };
    private              Map<Class<? extends SystemEvent>, SystemEventListener>      defaultListenersPerEvent     = new ConcurrentHashMap<>();
    private              Map<Class<? extends Component>, SystemEventListener>        defaultListenersPerComponent = new ConcurrentHashMap<>();
    private              Map<ComponentEventKey, SystemEventListener>                 listenerMap                  = new ConcurrentHashMap<>();
    private              Map<Class<? extends SystemEvent>, SystemEventPostprocessor> postprocessorMap             = new ConcurrentHashMap<>();
    private              Map<Class<? extends SystemEvent>, SystemEventPreprocessor>  preprocessorMap              = new ConcurrentHashMap<>();

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
        registerDefaultListenerPerEvent(SystemCursorPosEvent.class, new DefaultSystemCursorPosEventListener());
        registerDefaultListenerPerEvent(SystemMouseClickEvent.class, new DefaultSystemMouseClickEventListener());
        registerDefaultListenerPerEvent(SystemScrollEvent.class, new DefaultSystemScrollEventListener());
        registerDefaultListenerPerEvent(SystemScrollEvent.class, new DefaultSystemScrollEventListener());
        registerDefaultListenerPerEvent(SystemKeyEvent.class, new DefaultSystemKeyEventListener());
        registerDefaultListenerPerEvent(SystemCharEvent.class, new DefaultSystemCharEventListener());
    }

    public <C extends Component> void registerDefaultListenerPerComponent(Class<C> componentClass, SystemEventListener<C, SystemEvent> systemEventListener) {
        if (componentClass != null && systemEventListener != null) {
            defaultListenersPerComponent.put(componentClass, systemEventListener);
        }
    }

    private void initializeListeners() {
//        registerListener(ComponentContainer.class, SystemMouseClickEvent.class, new ContainerSystemMouseClickEventListener());

        registerListener(Component.class, SystemCursorPosEvent.class, new DefaultSystemCursorPosEventListener());

//        registerListener(CheckBox.class, SystemMouseClickEvent.class, new CheckBoxSystemMouseClickEventListener());

        registerListener(RadioButton.class, SystemMouseClickEvent.class, new RadioButtonSystemMouseClickListener());

        registerListener(ScrollBar.class, SystemCursorPosEvent.class, new ScrollBarSystemCursorPosEventListener());
        registerListener(ScrollBar.class, SystemMouseClickEvent.class, new ScrollBarSystemMouseClickEventListener());
        registerListener(ScrollBar.class, SystemScrollEvent.class, new ScrollBarSystemScrollEventListener());

        registerListener(Slider.class, SystemCursorPosEvent.class, new SliderSystemCursorPosEventListener());
        registerListener(Slider.class, SystemMouseClickEvent.class, new SliderSystemMouseClickEventListener());
        registerListener(Slider.class, SystemScrollEvent.class, new SliderSystemScrollEventListener());

        registerListener(TextInput.class, SystemCharEvent.class, new TextInputSystemCharEventListener());
        registerListener(TextInput.class, SystemCursorPosEvent.class, new TextInputSystemCursorPosEventListener());
        registerListener(TextInput.class, SystemKeyEvent.class, new TextInputSystemKeyEventProcessor());
        registerListener(TextInput.class, SystemMouseClickEvent.class, new TextInputSystemMouseClickEventListener());

        registerListener(TextArea.class, SystemCharEvent.class, new TextAreaSystemCharEventListener());
        registerListener(TextArea.class, SystemCursorPosEvent.class, new TextAreaSystemCursorPosEventListener());
        registerListener(TextArea.class, SystemKeyEvent.class, new TextAreaSystemKeyEventListener());
        registerListener(TextArea.class, SystemMouseClickEvent.class, new TextAreaSystemMouseClickEventListener());

        registerListener(ToggleButton.class, SystemMouseClickEvent.class, new ToggleButtonMouseClickEventProcessor());
    }

    public <C extends Component, E extends SystemEvent> void registerListener(Class<C> componentClass, Class<E> eventClass, SystemEventListener<C, E> listener) {
        if (listener != null && eventClass != null && componentClass != null) {
            listenerMap.put(new ComponentEventKey(componentClass, eventClass), listener);
        }
    }

    public <E extends SystemEvent> void registerDefaultListenerPerEvent(Class<E> eventClass, SystemEventListener<?, E> listener) {
        if (listener != null && eventClass != null) {
            defaultListenersPerEvent.put(eventClass, listener);
        }
    }

    @Override
    public <C extends Component, E extends SystemEvent> SystemEventListener getListener(Class<C> componentClass, Class<E> eventClass) {
        return cycledSearchOfListener(componentClass, eventClass, defaultListenersPerEvent.getOrDefault(eventClass, DEFAULT_EVENT_LISTENER));
    }

    @Override
    public <C extends Component, E extends SystemEvent> SystemEventListener<C, E> getDefaultListener(Class<E> eventcClass) {
        return defaultListenersPerEvent.get(eventcClass);
    }

    @Override
    public <E extends SystemEvent> SystemEventPreprocessor getPreprocessor(Class<E> eventClass) {
        return preprocessorMap.get(eventClass);
    }

    public <E extends SystemEvent, P extends SystemEventPreprocessor> void registerPreprocessor(Class<E> eventClass, P preprocessor) {
        if (eventClass != null && preprocessor != null) {
            preprocessorMap.put(eventClass, preprocessor);
        }
    }

    @Override
    public <E extends SystemEvent> SystemEventPostprocessor getPostprocessor(Class<E> eventClass) {
        return postprocessorMap.get(eventClass);
    }

    public <E extends SystemEvent, P extends SystemEventPostprocessor> void registerPostprocessor(Class<E> eventClass, P postprocessor) {
        if (eventClass != null && postprocessor != null) {
            postprocessorMap.put(eventClass, postprocessor);
        }
    }

    private <C extends Component, E extends SystemEvent> SystemEventListener
    cycledSearchOfListener(Class<C> componentClass, Class<E> eventClass, SystemEventListener defaultListener) {
        SystemEventListener listener = listenerMap.getOrDefault(new ComponentEventKey<>(componentClass, eventClass), defaultListenersPerComponent.get(componentClass));
        Class               cClass   = componentClass;
        while (listener == null) {
            listener = listenerMap.getOrDefault(new ComponentEventKey<>(cClass, eventClass), defaultListenersPerComponent.get(cClass));
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

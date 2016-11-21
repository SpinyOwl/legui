package org.liquidengine.legui.listener;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.processor.SystemEventPostprocessor;
import org.liquidengine.legui.processor.SystemEventPreprocessor;

/**
 * Created by Shcherbin Alexander on 10/24/2016.
 */
public abstract class SystemEventListenerProvider {

    public static SystemEventListenerProvider getProvider() {
        return PH.PROVIDER;
    }

    public static void setProvider(SystemEventListenerProvider provider) {
        PH.PROVIDER = provider;
    }

    public abstract <C extends Component, E extends SystemEvent> SystemEventListener getListener(Class<C> componentClass, Class<E> eventcClass);

    public abstract <E extends SystemEvent> SystemEventPreprocessor getPreprocessor(Class<E> eventClass);

    public abstract <E extends SystemEvent> SystemEventPostprocessor getPostprocessor(Class<E> eventClass);

    private static final class PH {
        private static SystemEventListenerProvider PROVIDER = new DefaultSystemEventListenerProvider();
    }
}

package org.liquidengine.legui.system.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.liquidengine.legui.system.event.SystemCharEvent;
import org.liquidengine.legui.system.event.SystemCursorPosEvent;
import org.liquidengine.legui.system.event.SystemEvent;
import org.liquidengine.legui.system.event.SystemKeyEvent;
import org.liquidengine.legui.system.event.SystemMouseClickEvent;
import org.liquidengine.legui.system.event.SystemScrollEvent;
import org.liquidengine.legui.system.event.SystemWindowCloseEvent;
import org.liquidengine.legui.system.event.SystemWindowFocusEvent;
import org.liquidengine.legui.system.event.SystemWindowIconifyEvent;
import org.liquidengine.legui.system.event.SystemWindowPosEvent;
import org.liquidengine.legui.system.event.SystemWindowRefreshEvent;
import org.liquidengine.legui.system.event.SystemWindowSizeEvent;

/**
 * Created by ShchAlexander on 1/25/2017.
 */
public class SystemEventHandlerProvider {

    private Map<Class<? extends SystemEvent>, SystemEventHandler<? extends SystemEvent>> processorMap = new ConcurrentHashMap<>();

    private SystemEventHandlerProvider() {
        registerProcessor(SystemWindowCloseEvent.class, new WindowCloseEventHandler());
        registerProcessor(SystemWindowIconifyEvent.class, new WindowIconifyEventHandler());
        registerProcessor(SystemScrollEvent.class, new ScrollEventHandler());
        registerProcessor(SystemWindowFocusEvent.class, new WindowFocusEventHandler());
        registerProcessor(SystemWindowPosEvent.class, new WindowPosEventHandler());
        registerProcessor(SystemWindowRefreshEvent.class, new WindowRefreshEventHandler());
        registerProcessor(SystemWindowSizeEvent.class, new WindowSizeEventHandler());
        registerProcessor(SystemCursorPosEvent.class, new CursorPosEventHandler());
        registerProcessor(SystemMouseClickEvent.class, new MouseClickEventHandler());
        registerProcessor(SystemKeyEvent.class, new KeyEventHandler());
        registerProcessor(SystemCharEvent.class, new CharEventHandler());

    }

    public static SystemEventHandlerProvider getInstance() {
        return LSEPPH.I;
    }

    public <E extends SystemEvent> void registerProcessor(Class<E> eventClass, SystemEventHandler<E> processor) {
        processorMap.put(eventClass, processor);
    }

    public <E extends SystemEvent> SystemEventHandler<E> getProcessor(Class<E> eventClass) {
        return (SystemEventHandler<E>) processorMap.get(eventClass);
    }

    private static class LSEPPH {

        private static final SystemEventHandlerProvider I = new SystemEventHandlerProvider();
    }
}

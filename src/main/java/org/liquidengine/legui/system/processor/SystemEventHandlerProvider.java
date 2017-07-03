package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.system.event.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class SystemEventHandlerProvider {
    private Map<Class<? extends LeguiSystemEvent>, LeguiSystemEventHandler<? extends LeguiSystemEvent>> processorMap = new ConcurrentHashMap<>();

    private SystemEventHandlerProvider() {
        registerProcessor(LeguiSystemWindowCloseEvent.class, new WindowCloseEventHandlerLegui());
        registerProcessor(LeguiSystemWindowIconifyEvent.class, new WindowIconifyEventHandlerLegui());
        registerProcessor(LeguiSystemScrollEvent.class, new ScrollEventHandlerLegui());
        registerProcessor(LeguiSystemWindowFocusEvent.class, new WindowFocusEventHandlerLegui());
        registerProcessor(LeguiSystemWindowPosEvent.class, new WindowPosEventHandlerLegui());
        registerProcessor(LeguiSystemWindowRefreshEvent.class, new WindowRefreshEventHandlerLegui());
        registerProcessor(LeguiSystemWindowSizeEvent.class, new WindowSizeEventHandlerLegui());
        registerProcessor(LeguiSystemCursorPosEvent.class, new CursorPosEventHandlerLegui());
        registerProcessor(LeguiSystemMouseClickEvent.class, new MouseClickEventHandlerLegui());
        registerProcessor(LeguiSystemKeyEvent.class, new KeyEventHandlerLegui());
        registerProcessor(LeguiSystemCharEvent.class, new CharEventHandlerLegui());

    }

    public static SystemEventHandlerProvider getInstance() {
        return LSEPPH.I;
    }

    public <E extends LeguiSystemEvent> void registerProcessor(Class<E> eventClass, LeguiSystemEventHandler<E> processor) {
        processorMap.put(eventClass, processor);
    }

    public <E extends LeguiSystemEvent> LeguiSystemEventHandler<E> getProcessor(Class<E> eventClass) {
        return (LeguiSystemEventHandler<E>) processorMap.get(eventClass);
    }

    private static class LSEPPH {
        private static final SystemEventHandlerProvider I = new SystemEventHandlerProvider();
    }
}

package com.spinyowl.legui.system.handler;

import com.spinyowl.legui.system.event.SystemCharEvent;
import com.spinyowl.legui.system.event.SystemCursorPosEvent;
import com.spinyowl.legui.system.event.SystemDropEvent;
import com.spinyowl.legui.system.event.SystemEvent;
import com.spinyowl.legui.system.event.SystemKeyEvent;
import com.spinyowl.legui.system.event.SystemMouseClickEvent;
import com.spinyowl.legui.system.event.SystemScrollEvent;
import com.spinyowl.legui.system.event.SystemWindowCloseEvent;
import com.spinyowl.legui.system.event.SystemWindowFocusEvent;
import com.spinyowl.legui.system.event.SystemWindowIconifyEvent;
import com.spinyowl.legui.system.event.SystemWindowPosEvent;
import com.spinyowl.legui.system.event.SystemWindowRefreshEvent;
import com.spinyowl.legui.system.event.SystemWindowSizeEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
    registerProcessor(SystemDropEvent.class, new DropEventHandler());

  }

  public static SystemEventHandlerProvider getInstance() {
    return LSEPPH.I;
  }

  public <E extends SystemEvent> void registerProcessor(Class<E> eventClass,
      SystemEventHandler<E> processor) {
    processorMap.put(eventClass, processor);
  }

  public <E extends SystemEvent> SystemEventHandler<E> getProcessor(Class<E> eventClass) {
    return (SystemEventHandler<E>) processorMap.get(eventClass);
  }

  private static class LSEPPH {

    private static final SystemEventHandlerProvider I = new SystemEventHandlerProvider();
  }
}

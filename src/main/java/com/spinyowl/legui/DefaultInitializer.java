package com.spinyowl.legui;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.listener.processor.EventProcessor;
import com.spinyowl.legui.listener.processor.EventProcessorProvider;
import com.spinyowl.legui.system.context.CallbackKeeper;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.context.DefaultCallbackKeeper;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessor;
import com.spinyowl.legui.system.handler.processor.SystemEventProcessorImpl;
import com.spinyowl.legui.system.renderer.Renderer;
import com.spinyowl.legui.system.renderer.nvg.NvgRenderer;

public class DefaultInitializer {

  private long window;
  private Frame frame;
  private Context context;
  private EventProcessor eventProcessor;
  private SystemEventProcessor systemEventProcessor;
  private CallbackKeeper callbackKeeper;
  private Renderer renderer;

  public DefaultInitializer(long window, Frame frame) {
    this.frame = frame;
    this.window = window;

    // We need to create legui context which shared by renderer and event processor.
    // Also we need to pass event processor for ui events such as click on component, key typing and etc.
    context = new Context(window);

    // We need to create callback keeper which will hold all of callbacks.
    // These callbacks will be used in initialization of system event processor
    // (will be added callbacks which will push system events to event queue and after that processed by SystemEventProcessor)
    callbackKeeper = new DefaultCallbackKeeper();
    // register callbacks for window. Note: all previously binded callbacks will be unbinded.
    CallbackKeeper.registerCallbacks(window, callbackKeeper);

    // Event processor for system events. System events should be processed and translated to gui events.
    systemEventProcessor = new SystemEventProcessorImpl();
    SystemEventProcessor.addDefaultCallbacks(callbackKeeper, systemEventProcessor);

    eventProcessor = EventProcessorProvider.getInstance();

    // Also we need to create initialize renderer provider
    // and create renderer which will render our ui components.
    renderer = new NvgRenderer();
  }

  public Context getContext() {
    return context;
  }

  public EventProcessor getGuiEventProcessor() {
    return eventProcessor;
  }

  public SystemEventProcessor getSystemEventProcessor() {
    return systemEventProcessor;
  }

  public Frame getFrame() {
    return frame;
  }

  public CallbackKeeper getCallbackKeeper() {
    return callbackKeeper;
  }

  public Renderer getRenderer() {
    return renderer;
  }

  public long getWindow() {
    return window;
  }
}

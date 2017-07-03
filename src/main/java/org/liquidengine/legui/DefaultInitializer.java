package org.liquidengine.legui;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.LeguiEventProcessor;
import org.liquidengine.legui.system.context.CallbackKeeper;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.context.DefaultCallbackKeeper;
import org.liquidengine.legui.system.processor.SystemEventProcessor;
import org.liquidengine.legui.system.renderer.LeguiRenderer;
import org.liquidengine.legui.system.renderer.LeguiRendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiRendererProvider;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class DefaultInitializer {
    private long                 window;
    private Frame                frame;
    private LeguiContext         context;
    private LeguiEventProcessor  eventProcessor;
    private SystemEventProcessor systemEventProcessor;
    private CallbackKeeper       callbackKeeper;
    private LeguiRenderer        renderer;

    public DefaultInitializer(long window, Frame frame) {
        this.frame = frame;
        this.window = window;

        // We need to create legui context which shared by renderer and event processor.
        // Also we need to pass event processor for ui events such as click on component, key typing and etc.
        context = new LeguiContext(window, frame, eventProcessor = new LeguiEventProcessor());

        // We need to create callback keeper which will hold all of callbacks.
        // These callbacks will be used in initialization of system event processor
        // (will be added callbacks which will push system events to event queue and after that processed by SystemEventProcessor)
        callbackKeeper = new DefaultCallbackKeeper();
        // register callbacks for window. Note: all previously binded callbacks will be unbinded.
        ((DefaultCallbackKeeper) callbackKeeper).registerCallbacks(this.window);

        // LeguiEvent processor for system events. System events should be processed and translated to gui events.
        systemEventProcessor = new SystemEventProcessor(this.frame, context, callbackKeeper);

        // Also we need to create initialize renderer provider
        // and create renderer which will render our ui components.
        NvgLeguiRendererProvider provider = NvgLeguiRendererProvider.getInstance();
        LeguiRendererProvider.setRendererProvider(provider);
        renderer = new NvgLeguiRenderer(context, provider);
    }

    public LeguiContext getContext() {
        return context;
    }

    public LeguiEventProcessor getGuiEventProcessor() {
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

    public LeguiRenderer getRenderer() {
        return renderer;
    }

    public long getWindow() {
        return window;
    }
}

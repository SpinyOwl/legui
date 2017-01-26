package org.liquidengine.legui;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.LeguiEventProcessor;
import org.liquidengine.legui.system.context.DefaultLeguiCallbackKeeper;
import org.liquidengine.legui.system.context.LeguiCallbackKeeper;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.processor.LeguiSystemEventProcessorManager;
import org.liquidengine.legui.system.renderer.LeguiRenderer;
import org.liquidengine.legui.system.renderer.LeguiRendererProvider;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgRendererProvider;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class Legui {
    private long                             window;
    private Frame                            frame;
    private LeguiContext                     context;
    private LeguiEventProcessor              eventProcessor;
    private LeguiSystemEventProcessorManager systemEventProcessorManager;
    private LeguiCallbackKeeper              callbackKeeper;
    private LeguiRenderer                    renderer;

    public Legui(long window, Frame frame) {
        this.frame = frame;
        this.window = window;

        // We need to create legui context which shared by renderer and event processor.
        // Also we need to pass event processor for ui events such as click on component, key typing and etc.
        context = new LeguiContext(window, frame, eventProcessor = new LeguiEventProcessor());

        // We need to create callback keeper which will hold all of callbacks.
        // These callbacks will be used in initialization of system event processor
        // (will be added callbacks which will push system events to event queue and after that processed by SystemEventProcessor)
        callbackKeeper = new DefaultLeguiCallbackKeeper();
        // register callbacks for window. Note: all previously binded callbacks will be unbinded.
        ((DefaultLeguiCallbackKeeper) callbackKeeper).registerCallbacks(this.window);

        // Event processor for system events. System events should be processed and translated to gui events.
        systemEventProcessorManager = new LeguiSystemEventProcessorManager(this.frame, context, callbackKeeper);

        // Also we need to create initialize renderer provider
        // and create renderer which will render our ui components.
        NvgRendererProvider provider = new NvgRendererProvider();
        LeguiRendererProvider.setRendererProvider(provider);
        renderer = new NvgLeguiRenderer(context, provider);
    }

    public LeguiContext getContext() {
        return context;
    }

    public LeguiEventProcessor getGuiEventProcessor() {
        return eventProcessor;
    }

    public LeguiSystemEventProcessorManager getSystemEventProcessorManager() {
        return systemEventProcessorManager;
    }

    public Frame getFrame() {
        return frame;
    }

    public LeguiCallbackKeeper getCallbackKeeper() {
        return callbackKeeper;
    }

    public LeguiRenderer getRenderer() {
        return renderer;
    }

    public long getWindow() {
        return window;
    }
}

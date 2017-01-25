package org.liquidengine.legui;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.listener.LeguiEventProcessor;
import org.liquidengine.legui.system.context.DefaultLeguiCallbackKeeper;
import org.liquidengine.legui.system.context.LeguiCallbackKeeper;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.processor.LeguiSystemEventProcessorManager;
import org.liquidengine.legui.system.renderer.LeguiRenderer;
import org.liquidengine.legui.system.renderer.LeguiRendererProvider;

/**
 * Created by Aliaksandr_Shcherbin on 1/25/2017.
 */
public class Legui {
    private Frame                            frame;
    private LeguiContext                     context;
    private LeguiEventProcessor              eventProcessor;
    private LeguiSystemEventProcessorManager systemEventProcessorManager;
    private LeguiCallbackKeeper              callbackKeeper;
    private LeguiRenderer                    renderer;

    public Legui(long window, Frame frame) {
        context = new LeguiContext();
        eventProcessor = new LeguiEventProcessor();
        DefaultLeguiCallbackKeeper defaultLeguiCallbackKeeper = new DefaultLeguiCallbackKeeper();
        defaultLeguiCallbackKeeper.registerCallbacks(window);
        callbackKeeper = defaultLeguiCallbackKeeper;
        systemEventProcessorManager = new LeguiSystemEventProcessorManager(frame, context, callbackKeeper);
        renderer = LeguiRendererProvider.getInstance().getRenderer();
    }

    public void initialize() {
        renderer.initialize();
    }

    public void destroy() {
        renderer.destroy();
    }
}

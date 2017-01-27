package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.context.ILeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.context.LeguiSystemEventQueue;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.listener.SystemEventListener;
import org.liquidengine.legui.listener.SystemEventListenerProvider;

import java.util.List;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class SystemEventProcessor {

    private final Frame                 frame;
    private final LeguiContext          context;
    private final LeguiSystemEventQueue leguiEventQueue;
    private final ILeguiCallbackKeeper  callbackKeeper;

    public SystemEventProcessor(LeguiContext context, ILeguiCallbackKeeper callbackKeeper) {
        this.frame = context.getFrame();
        this.context = context;
        this.callbackKeeper = callbackKeeper;
        leguiEventQueue = new LeguiSystemEventQueue(callbackKeeper);
    }

    public LeguiContext getContext() {
        return context;
    }

    public ILeguiCallbackKeeper getCallbackKeeper() {
        return callbackKeeper;
    }

    /**
     * Used to translate events to gui. Usually it used to pass event to main UI element (Main Panel).
     * <p>
     * If gui element is frame than it will be proceed as any other gui element and event will be passed
     * to all child element
     */
    public void processEvent() {
        SystemEvent event;
        while ((event = leguiEventQueue.poll()) != null) {
            SystemEventListenerProvider provider = SystemEventListenerProvider.getProvider();

            SystemEventPreprocessor preprocessor = provider.getPreprocessor(event.getClass());
            if (preprocessor != null) preprocessor.process(event, context);

            List<Layer> allLayers = frame.getAllLayers();
            for (Layer layer : allLayers) {
                Layer.LayerContainer layerContainer = layer.getContainer();
                SystemEventListener  listener       = layerContainer.getSystemEventListeners().getListener(event.getClass());
                if (listener != null) listener.update(event, layerContainer, context);
            }

            SystemEventPostprocessor postprocessor = provider.getPostprocessor(event.getClass());
            if (postprocessor != null) postprocessor.process(event, context);
        }
    }
}

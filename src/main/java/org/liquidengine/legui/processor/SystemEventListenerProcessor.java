package org.liquidengine.legui.processor;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiCallbackKeeper;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.context.LeguiSystemEventQueue;
import org.liquidengine.legui.event.SystemEvent;
import org.liquidengine.legui.listener.SystemEventListener;

/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class SystemEventListenerProcessor {

    private final Component mainGuiComponent;
    private final LeguiContext context;
    private final LeguiSystemEventQueue leguiEventQueue;
    private final LeguiCallbackKeeper callbackKeeper;

    public SystemEventListenerProcessor(Component mainGuiComponent, LeguiContext context, LeguiCallbackKeeper callbackKeeper) {
        this.mainGuiComponent = mainGuiComponent;
        this.context = context;
        this.callbackKeeper = callbackKeeper;
        leguiEventQueue = new LeguiSystemEventQueue(callbackKeeper);
    }

    public LeguiContext getContext() {
        return context;
    }

    public LeguiCallbackKeeper getCallbackKeeper() {
        return callbackKeeper;
    }

    /**
     * Used to translate events to gui. Usually it used to pass event to main UI element (Main Panel).
     * <p>
     * If gui element is mainGuiComponent than it will be proceed as any other gui element and event will be passed
     * to all child element
     */
    public void processEvent() {
        SystemEvent event = leguiEventQueue.poll();
        if (event != null) {
            SystemEventListenerProvider provider = SystemEventListenerProvider.getProvider();

            SystemEventPreprocessor preprocessor = provider.getPreprocessor(event.getClass());
            if (preprocessor != null) preprocessor.process(event, context);

            SystemEventListener listener = mainGuiComponent.getSystemEventListeners().getListener(event.getClass());
            if (listener != null) listener.update(event, mainGuiComponent, context);

            SystemEventPostprocessor postprocessor = provider.getPostprocessor(event.getClass());
            if (postprocessor != null) postprocessor.process(event, context);
        }
    }
}

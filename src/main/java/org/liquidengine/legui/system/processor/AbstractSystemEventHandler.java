package org.liquidengine.legui.system.processor;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.event.SystemEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/9/2017.
 */
public abstract class AbstractSystemEventHandler<E extends SystemEvent> implements SystemEventHandler<E> {
    public final void process(E event, Frame frame, Context context) {
        preProcess(event, frame, context);
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                if (!layer.getContainer().isVisible() || !layer.getContainer().isEnabled()) continue;
                if (process(event, layer, context)) {
                    return;
                }
            }
            if (!layer.isEventPassable()) return;
        }
        postProcess(event, frame, context);
    }

    protected void preProcess(E event, Frame frame, Context context) {
    }

    protected boolean process(E event, Layer layer, Context context) {
        return false;
    }

    protected void postProcess(E event, Frame frame, Context context) {
    }
}

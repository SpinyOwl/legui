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
public abstract class AbstractEventHandler<E extends SystemEvent> {
    public void process(E event, Frame frame, Context context) {
        preProcess(event, frame, context);
        List<Layer> layers = frame.getAllLayers();
        Collections.reverse(layers);
        for (Layer layer : layers) {
            if (layer.isEventReceivable()) {
                process(event, layer, context);
            }
            if (!layer.isEventPassable()) return;
        }
        postProcess(event, frame, context);
    }

    public abstract void preProcess(E event, Frame frame, Context context);

    public abstract void process(E event, Layer layer, Context context);

    public abstract void postProcess(E event, Frame frame, Context context);
}

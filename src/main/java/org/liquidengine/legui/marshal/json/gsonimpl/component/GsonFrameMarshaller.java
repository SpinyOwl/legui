package org.liquidengine.legui.marshal.json.gsonimpl.component;

import static org.liquidengine.legui.marshal.JsonConstants.COMPONENT_LAYER;
import static org.liquidengine.legui.marshal.JsonConstants.LAYERS;
import static org.liquidengine.legui.marshal.JsonConstants.TOOLTIP_LAYER;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ComponentLayer;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.TooltipLayer;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

/**
 * Used to marshal and unmarshal {@link Frame}.
 */
public class GsonFrameMarshaller<T extends Frame> extends AbstractGsonMarshaller<T> {


    /**
     * Reads data from object and puts it to json object.
     *
     * @param object object to read.
     * @param json json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        JsonObject componentLayer = GsonMarshalUtil.marshalToJson(object.getComponentLayer());
        JsonArray layers = new JsonArray();
        for (Layer l : object.getLayers()) {
            layers.add(GsonMarshalUtil.marshalToJson(l, context));
        }
        JsonObject tooltipLayer = GsonMarshalUtil.marshalToJson(object.getTooltipLayer());
        GsonUtil.fill(json)
            .add(COMPONENT_LAYER, componentLayer)
            .add(LAYERS, layers)
            .add(TOOLTIP_LAYER, tooltipLayer)
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json json object to read.
     * @param object object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        JsonElement layers = json.get(LAYERS);
        if (isNotNull(layers) && layers.isJsonArray()) {
            JsonArray ls = layers.getAsJsonArray();
            for (JsonElement layer : ls) {
                processLayer(object, layer, context);
            }
        }
        JsonElement tl = json.get(TOOLTIP_LAYER);
        if (isNotNull(tl) && tl.isJsonObject()) {
            Object layer = GsonMarshalUtil.unmarshal(tl.getAsJsonObject(), context);
            if (layer instanceof TooltipLayer) {
                TooltipLayer l = (TooltipLayer) layer;
                object.getTooltipLayer().setEventPassable(l.isEventPassable());
                object.getTooltipLayer().setEventReceivable(l.isEventReceivable());
                object.getTooltipLayer().setContainer(l.getContainer());
            }
        }
        JsonElement cl = json.get(COMPONENT_LAYER);
        if (isNotNull(cl) && cl.isJsonObject()) {
            Object layer = GsonMarshalUtil.unmarshal(cl.getAsJsonObject(), context);
            if (layer instanceof ComponentLayer) {
                ComponentLayer l = (ComponentLayer) layer;
                object.getComponentLayer().setEventPassable(l.isEventPassable());
                object.getComponentLayer().setEventReceivable(l.isEventReceivable());
                object.getComponentLayer().setContainer(l.getContainer());
            }
        }
    }

    private void processLayer(T object, JsonElement layer, GsonMarshalContext context) {
        if (isNotNull(layer) && layer.isJsonObject()) {
            Object l = GsonMarshalUtil.unmarshal(layer.getAsJsonObject(), context);
            if (l instanceof Layer) {
                object.addLayer((Layer) l);
            }
        }
    }
}

package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.*;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Marshaller for {@link Layer}.
 */
public class GsonLayerMarshaller<T extends Layer> extends AbstractGsonMarshaller<T> {
    /**
     * Reads data from object and puts it to json object.
     *
     * @param object  object to read.
     * @param json    json object to fill.
     * @param context marshal context.
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        GsonUtil.fill(json)
                .add(CONTAINER, GsonMarshalUtil.marshalToJson(object.getContainer()))
                .add(EVENT_PASSABLE, object.isEventPassable())
                .add(EVENT_RECEIVABLE, object.isEventReceivable())
        ;
    }

    /**
     * Reads data from json object and puts it to object.
     *
     * @param json    json object to read.
     * @param object  object to fill.
     * @param context marshal context.
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        JsonElement container = json.get(CONTAINER);
        JsonElement eventPassable = json.get(EVENT_PASSABLE);
        JsonElement eventReceivable = json.get(EVENT_RECEIVABLE);

        if (isNotNull(container)) object.setContainer(GsonMarshalUtil.unmarshal(container.getAsJsonObject(), context));
        if (isNotNull(eventPassable)) object.setEventPassable(eventPassable.getAsBoolean());
        if (isNotNull(eventReceivable)) object.setEventReceivable(eventReceivable.getAsBoolean());
    }
}

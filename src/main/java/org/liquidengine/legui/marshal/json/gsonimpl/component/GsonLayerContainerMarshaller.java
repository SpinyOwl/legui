package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;

/**
 * Marshaller for {@link LayerContainer}.
 */
public class GsonLayerContainerMarshaller<T extends LayerContainer> extends GsonContainerMarshaller<T> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        super.marshal(object, json, context);
    }

    /**
     * Reads data from json object and puts it to object
     *
     * @param json    json object to read
     * @param object  object to fill
     * @param context marshal context
     */
    @Override
    protected void unmarshal(JsonObject json, T object, GsonMarshalContext context) {
        super.unmarshal(json, object, context);
    }
}

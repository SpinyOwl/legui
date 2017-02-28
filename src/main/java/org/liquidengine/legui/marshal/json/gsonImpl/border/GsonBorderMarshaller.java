package org.liquidengine.legui.marshal.json.gsonImpl.border;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.marshal.json.gsonImpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.ENABLED;
import static org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil.isNotNull;

/**
 * Marshaller for {@link Border}
 */
public class GsonBorderMarshaller<T extends Border> extends AbstractGsonMarshaller<T> {

    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {
        GsonUtil.fill(json)
                .add(ENABLED, object.isEnabled());
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
        JsonElement isEnabled = json.get(ENABLED);
        if (isNotNull(isEnabled)) object.setEnabled(isEnabled.getAsBoolean());
    }
}

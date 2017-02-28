package org.liquidengine.legui.marshal.json.gsonImpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonUtil;

/**
 * Created by ShchAlexander on 27.02.2017.
 */
public class GsonControllerMarshaller<T extends Controller> extends GsonComponentMarshaller<T> {
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

        JsonObject jsonObject = GsonMarshalUtil.marshalToJson(object.getTooltip(), context);
        GsonUtil.fill(json).add("tooltip", jsonObject);
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

        JsonElement tooltip = json.get("tooltip");
        if (GsonUtil.isNotNull(tooltip) && tooltip.isJsonObject()) object.setTooltipComponent(GsonMarshalUtil.unmarshal((JsonObject) tooltip));
    }
}

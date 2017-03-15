package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil;

import static org.liquidengine.legui.marshal.JsonConstants.PROGRESS_COLOR;
import static org.liquidengine.legui.marshal.JsonConstants.VALUE;
import static org.liquidengine.legui.marshal.json.gsonimpl.GsonUtil.isNotNull;

/**
 * Created by ShchAlexander on 27.02.2017.
 */
public class GsonProgressBarMarshaller<T extends ProgressBar> extends GsonControllerMarshaller<T> {
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

        GsonUtil.fill(json)
                .add(VALUE, object.getValue())
                .add(PROGRESS_COLOR, GsonUtil.createColor(object.getBackgroundColor()))
        ;
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

        JsonElement value = json.get(VALUE);
        JsonObject  pc    = json.getAsJsonObject(PROGRESS_COLOR);

        if (isNotNull(value)) object.setValue(value.getAsFloat());
        if (isNotNull(pc)) object.setBackgroundColor(GsonUtil.readColor(pc));
    }
}

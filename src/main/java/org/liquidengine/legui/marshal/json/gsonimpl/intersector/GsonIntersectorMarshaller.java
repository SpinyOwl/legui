package org.liquidengine.legui.marshal.json.gsonimpl.intersector;

import com.google.gson.JsonObject;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.marshal.json.gsonimpl.AbstractGsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;

/**
 * Created by ShchAlexander on 26.02.2017.
 */
public class GsonIntersectorMarshaller<T extends Intersector> extends AbstractGsonMarshaller<T> {
    /**
     * Reads data from object and puts it to json object
     *
     * @param object  object to read
     * @param json    json object to fill
     * @param context marshal context
     */
    @Override
    protected void marshal(T object, JsonObject json, GsonMarshalContext context) {

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

    }
}

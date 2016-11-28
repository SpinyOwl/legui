package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonBuilder {

    private JsonObject object;

    private GsonBuilder(JsonObject object) {
        this.object = object;
    }

    public static final GsonBuilder create() {
        return new GsonBuilder(new JsonObject());
    }

    public static final GsonBuilder fill(JsonObject object) {
        return new GsonBuilder(object != null ? object : new JsonObject());
    }

    public GsonBuilder add(String property, JsonElement value) {
        object.add(property, value);
        return this;
    }

    public GsonBuilder remove(String property) {
        object.remove(property);
        return this;
    }

    public GsonBuilder add(String property, String value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonBuilder add(String property, Number value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonBuilder add(String property, Boolean value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonBuilder add(String property, Character value) {
        object.addProperty(property, value);
        return this;
    }

    public JsonObject get() {
        return object;
    }

}

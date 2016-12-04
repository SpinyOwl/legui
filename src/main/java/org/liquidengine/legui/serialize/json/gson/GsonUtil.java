package org.liquidengine.legui.serialize.json.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector4f;

import static org.liquidengine.legui.serialize.json.gson.GsonConstants.*;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonUtil {

    private JsonObject object;

    private GsonUtil(JsonObject object) {
        this.object = object;
    }

    public static final GsonUtil create() {
        return new GsonUtil(new JsonObject());
    }

    public static final GsonUtil fill(JsonObject object) {
        return new GsonUtil(object != null ? object : new JsonObject());
    }

    public static JsonObject createColor(Vector4f color) {
        return GsonUtil.create()
                .add(R, color.x)
                .add(G, color.y)
                .add(B, color.z)
                .add(A, color.w)
                .get();
    }

    public static Vector4f readColor(JsonObject color) {
        if (color == null || color.isJsonNull()) return null;
        Vector4f bgc = new Vector4f();

        JsonElement r = color.get(R);
        JsonElement g = color.get(G);
        JsonElement b = color.get(B);
        JsonElement a = color.get(A);

        if (isNotNull(r)) bgc.x = r.getAsFloat();
        if (isNotNull(g)) bgc.y = g.getAsFloat();
        if (isNotNull(b)) bgc.z = b.getAsFloat();
        if (isNotNull(a)) bgc.w = a.getAsFloat();

        return bgc;
    }

    public static boolean isNotNull(JsonElement element) {
        return element != null && !element.isJsonNull();
    }
    public static boolean isJsonNull(JsonElement element) {
        return element != null && element.isJsonNull();
    }

    public GsonUtil add(String property, JsonElement value) {
        object.add(property, value);
        return this;
    }

    public GsonUtil remove(String property) {
        object.remove(property);
        return this;
    }

    public GsonUtil add(String property, String value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonUtil add(String property, Number value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonUtil add(String property, Boolean value) {
        object.addProperty(property, value);
        return this;
    }

    public GsonUtil add(String property, Character value) {
        object.addProperty(property, value);
        return this;
    }

    public JsonObject get() {
        return object;
    }
}

package org.liquidengine.legui.marshal.json.gsonimpl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liquidengine.legui.image.DummyImageLoader;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.util.IOUtil;

/**
 * Used implement tests for Gson marshallers.
 */
public class GsonMarshallingTestBase {

    static {
        ImageLoader.setLoader(new DummyImageLoader());
    }

    /**
     * Used to parse json from file to JsonObject.
     *
     * @param path path to file.
     * @return parsed {@link JsonObject} instance.
     */
    public JsonObject readJsonFromFile(String path) {
        return new JsonParser().parse(IOUtil.loadResourceAsString(path)).getAsJsonObject();
    }

}

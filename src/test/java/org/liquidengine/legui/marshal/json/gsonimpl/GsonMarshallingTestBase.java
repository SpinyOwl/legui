package org.liquidengine.legui.marshal.json.gsonimpl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.liquidengine.legui.image.DummyImageLoader;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.leutil.io.IOUtil;

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
        try {
            return new JsonParser().parse(IOUtil.resourceToString(path)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}

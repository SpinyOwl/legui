package org.liquidengine.legui.marshal.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.liquidengine.legui.image.DummyImageLoader;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.util.IOUtil;

/**
 * Created by ShchAlexander on 09.04.2017.
 */
public class JsonTestBase {

    @Before
    public void initialize() {
        ImageLoader.setLoader(new DummyImageLoader());
    }

    public JsonObject readJsonFromFile(String path) {
        String     json   = IOUtil.loadResourceAsString(path);
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonObject();
    }
}

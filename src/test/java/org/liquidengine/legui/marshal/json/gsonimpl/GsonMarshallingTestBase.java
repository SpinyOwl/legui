package org.liquidengine.legui.marshal.json.gsonimpl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.liquidengine.legui.component.Component;
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
     * Marshaller context.
     */
    private GsonMarshalContext context;

    @Before
    public void initialize() {
        context = new GsonMarshalContext();
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

    /**
     * Used to test marshaller.
     *
     * @param toMarshal  object to marshal (standard).
     * @param marshaller marshaller to test.
     * @param pathToJson path to json file with standard model.
     * @param <T>        type of object.
     * @param <M>        type of marshaller.
     */
    protected <T, M extends AbstractGsonMarshaller<T>> void testMarshal(T toMarshal, M marshaller, String pathToJson) {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(toMarshal, context);
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test marshaller.
     *
     * @param expected   expected object (standard).
     * @param marshaller marshaller.
     * @param pathToJson path to json file with standard model which will be unmarshalled.
     * @param <T>        type of object.
     * @param <M>        type of marshaller.
     */
    protected <T, M extends AbstractGsonMarshaller<T>> void testUnmarshal(T expected, M marshaller, String pathToJson) {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        T          actual          = marshaller.unmarshal(jsonToUnmarshal, context);
        Assert.assertEquals(expected, actual);
    }
}

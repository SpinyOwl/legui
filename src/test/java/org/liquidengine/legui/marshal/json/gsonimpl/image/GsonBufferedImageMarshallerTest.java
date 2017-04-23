package org.liquidengine.legui.marshal.json.gsonimpl.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for ImageIcon Gson Marshaller.
 */
public class GsonBufferedImageMarshallerTest extends GsonMarshallingTestBase {

    private BufferedImage                              icon       = new BufferedImage("org/liquidengine/legui/marshal/json/gsonimpl/image/test.png");
    private GsonBufferedImageMarshaller<BufferedImage> marshaller = new GsonBufferedImageMarshaller<>();
    private String                                     pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/image/BufferedImage.json";

    /**
     * Used to test {@link GsonBufferedImageMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(icon, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonBufferedImageMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject    jsonToUnmarshal = readJsonFromFile(pathToJson);
        BufferedImage actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(icon, actual);
    }
}
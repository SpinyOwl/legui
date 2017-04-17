package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.joml.Vector2f;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for ImageIcon Gson Marshaller.
 */
public class GsonImageIconMarshallerTest extends GsonMarshallingTestBase {

    private ImageIcon                          icon       = new ImageIcon(new Vector2f(16), ImageLoader.loadImage("test/image.png"));
    private GsonImageIconMarshaller<ImageIcon> marshaller = new GsonImageIconMarshaller<>();
    private String                             pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/icon/ImageIcon.json";

    /**
     * Used to test {@link GsonImageIconMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
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
     * Used to test {@link GsonImageIconMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        ImageIcon  actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(icon, actual);
    }

}
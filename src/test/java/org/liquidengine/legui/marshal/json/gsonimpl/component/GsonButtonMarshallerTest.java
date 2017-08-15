package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for Button Gson Marshaller.
 */
public class GsonButtonMarshallerTest extends GsonMarshallingTestBase {

    private Button button = new Button("Test Button", 10, 20, 30, 40);
    private GsonButtonMarshaller<Button> marshaller = new GsonButtonMarshaller<>();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/Button.json";

    /**
     * Used to test {@link GsonButtonMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual = marshaller.marshal(button, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonButtonMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        Button actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(button, actual);
    }

}
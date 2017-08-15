package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for RadioButton Gson Marshaller.
 */
public class GsonRadioButtonMarshallerTest extends GsonMarshallingTestBase {

    private RadioButton button = new RadioButton("Test Button", 10, 20, 30, 40);
    private GsonRadioButtonMarshaller<RadioButton> marshaller = new GsonRadioButtonMarshaller<>();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/RadioButton.json";

    /**
     * Used to test {@link GsonRadioButtonMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
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
     * Used to test {@link GsonRadioButtonMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        RadioButton actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(button, actual);
    }

}
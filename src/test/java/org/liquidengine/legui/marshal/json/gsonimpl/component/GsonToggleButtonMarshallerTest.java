package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for ToggleButton Gson Marshaller.
 */
public class GsonToggleButtonMarshallerTest extends GsonMarshallingTestBase {

    private ToggleButton toggleButton = new ToggleButton("Test toggleButton", 10, 30, 50, 70);
    private GsonToggleButtonMarshaller<ToggleButton> marshaller = new GsonToggleButtonMarshaller<>();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/ToggleButton.json";

    /**
     * Used to test {@link GsonToggleButtonMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual = marshaller.marshal(toggleButton, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonToggleButtonMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        ToggleButton actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(toggleButton, actual);
    }


}
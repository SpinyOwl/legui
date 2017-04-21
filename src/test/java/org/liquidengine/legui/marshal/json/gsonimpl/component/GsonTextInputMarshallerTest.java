package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for TextInput Gson Marshaller.
 */
public class GsonTextInputMarshallerTest extends GsonMarshallingTestBase {
    private TextInput                          textInput  = new TextInput("Test text input", 10, 30, 50, 70);
    private GsonTextInputMarshaller<TextInput> marshaller = new GsonTextInputMarshaller<>();
    private String                             pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/TextInput.json";

    /**
     * Used to test {@link GsonTextInputMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(textInput, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonTextInputMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        TextInput  actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(textInput, actual);
    }

}
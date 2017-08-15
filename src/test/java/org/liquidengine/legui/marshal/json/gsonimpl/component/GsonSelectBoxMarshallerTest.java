package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for SelectBox Gson Marshaller.
 */
public class GsonSelectBoxMarshallerTest extends GsonMarshallingTestBase {

    private SelectBox selectBox = new SelectBox(10, 30, 50, 70);
    private GsonSelectBoxMarshaller<SelectBox> marshaller = new GsonSelectBoxMarshaller<>();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/SelectBox.json";

    /**
     * Initialize select box with two elements.
     */
    @Before
    public void initialize() {
        selectBox.addElement("Hello");
        selectBox.addElement("World");
    }

    /**
     * Used to test {@link GsonSelectBoxMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual = marshaller.marshal(selectBox, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonSelectBoxMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        SelectBox actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(selectBox, actual);
    }

}
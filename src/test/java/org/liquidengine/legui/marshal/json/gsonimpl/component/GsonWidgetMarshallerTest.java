package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;


/**
 * Test for Label Gson Marshaller.
 */
public class GsonWidgetMarshallerTest extends GsonMarshallingTestBase {

    private Widget label = new Widget();
    private GsonWidgetMarshaller<Widget> marshaller = new GsonWidgetMarshaller<>();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/Widget.json";

    /**
     * Used to test {@link GsonWidgetMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual = marshaller.marshal(label, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonWidgetMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        Widget actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(label, actual);
    }

}
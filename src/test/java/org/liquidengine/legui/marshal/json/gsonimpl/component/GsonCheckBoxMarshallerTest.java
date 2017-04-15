package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Created by ShchAlexander on 15.04.2017.
 */
public class GsonCheckBoxMarshallerTest extends GsonMarshallingTestBase {
    private CheckBox                         checkbox   = new CheckBox("Test Checkbox", 10, 30, 50, 70);
    private GsonCheckBoxMarshaller<CheckBox> marshaller = new GsonCheckBoxMarshaller();
    private String                           pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/Checkbox.json";

    @Test
    public void marshal() throws Exception {
    String expected = readJsonFromFile(pathToJson).toString();
    String actual   = marshaller.marshal(checkbox, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
}

    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        CheckBox   actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(checkbox, actual);
    }

}
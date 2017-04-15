package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Created by ShchAlexander on 16.04.2017.
 */
public class GsonLabelMarshallerTest extends GsonMarshallingTestBase {
    private Label                      label      = new Label("Test Label", 10, 30, 50, 70);
    private GsonLabelMarshaller<Label> marshaller = new GsonLabelMarshaller<>();
    private String                     pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/Label.json";

    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(label, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        Label      actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(label, actual);
    }

}
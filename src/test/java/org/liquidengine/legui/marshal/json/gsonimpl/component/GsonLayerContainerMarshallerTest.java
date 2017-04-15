package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Created by ShchAlexander on 16.04.2017.
 */
public class GsonLayerContainerMarshallerTest extends GsonMarshallingTestBase {
    private LayerContainer                               layerContainer = new LayerContainer();
    private GsonLayerContainerMarshaller<LayerContainer> marshaller     = new GsonLayerContainerMarshaller<>();
    private String                                       pathToJson     = "org/liquidengine/legui/marshal/json/gsonimpl/component/LayerContainer.json";

    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(layerContainer, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void unmarshal() throws Exception {
        JsonObject     jsonToUnmarshal = readJsonFromFile(pathToJson);
        LayerContainer actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(layerContainer, actual);
    }

}
package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Created by ShchAlexander on 16.04.2017.
 */
public class GsonImageViewMarshallerTest extends GsonMarshallingTestBase {
    private ImageView                          imageView  = new ImageView();
    private GsonImageViewMarshaller<ImageView> marshaller = new GsonImageViewMarshaller<>();
    private String                             pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/ImageView.json";

    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(imageView, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        ImageView  actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(imageView, actual);
    }

}
package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Created by ShchAlexander on 15.04.2017.
 */
public class GsonFrameMarshallerTest extends GsonMarshallingTestBase {


    private Frame                      frame      = new Frame();
    private GsonFrameMarshaller<Frame> marshaller = new GsonFrameMarshaller<>();
    private String                     pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/component/Frame.json";

    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(frame, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        Frame      actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(frame, actual);
    }

}
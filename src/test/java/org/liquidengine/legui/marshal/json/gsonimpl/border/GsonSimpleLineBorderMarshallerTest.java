package org.liquidengine.legui.marshal.json.gsonimpl.border;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for SimpleLineBorder Gson Marshaller.
 */
public class GsonSimpleLineBorderMarshallerTest extends GsonMarshallingTestBase {

    private SimpleLineBorder border = new SimpleLineBorder(ColorConstants.red(), 1.2f);
    private GsonSimpleLineBorderMarshaller marshaller = new GsonSimpleLineBorderMarshaller();
    private String pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/border/SimpleLineBorder.json";

    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#marshal(Object, JsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual = marshaller.marshal(border, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }


    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#unmarshal(JsonElement, GsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        SimpleLineBorder actual = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(border, actual);
    }

}
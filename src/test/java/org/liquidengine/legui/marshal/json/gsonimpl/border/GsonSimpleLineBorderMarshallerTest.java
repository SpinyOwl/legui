package org.liquidengine.legui.marshal.json.gsonimpl.border;

import com.google.gson.JsonElement;
import org.junit.Test;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;

/**
 * Test for SimpleLineBorder Gson Marshaller.
 */
public class GsonSimpleLineBorderMarshallerTest extends GsonMarshallingTestBase {
    private SimpleLineBorder               border     = new SimpleLineBorder(ColorConstants.red(), 1.2f);
    private GsonSimpleLineBorderMarshaller marshaller = new GsonSimpleLineBorderMarshaller();
    private String                         pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/border/SimpleLineBorder.json";

    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#marshal(Object, JsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        testMarshal(border, marshaller, pathToJson);
    }


    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#unmarshal(JsonElement, GsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        testUnmarshal(border, marshaller, pathToJson);
    }

}
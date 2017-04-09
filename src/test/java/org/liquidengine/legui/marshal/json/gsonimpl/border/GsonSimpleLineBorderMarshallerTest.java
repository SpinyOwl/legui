package org.liquidengine.legui.marshal.json.gsonimpl.border;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.JsonTestBase;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

import static org.junit.Assert.*;

/**
 * Test for SimpleLineBorder Gson Marshaller.
 */
public class GsonSimpleLineBorderMarshallerTest extends JsonTestBase {
    /**
     * Marshaller to test.
     */
    private GsonSimpleLineBorderMarshaller marshaller;
    /**
     * Marshaller context.
     */
    private GsonMarshalContext             context;

    /**
     * Used to initialize test.
     */
    @Before
    public void setUp() {
        context = new GsonMarshalContext();
        marshaller = new GsonSimpleLineBorderMarshaller();
    }

    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#marshal(Object, JsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        SimpleLineBorder border   = new SimpleLineBorder(ColorConstants.red(), 1.2f);
        JsonObject       expected = readJsonFromFile("org/liquidengine/legui/marshal/json/gsonimpl/border/SimpleLineBorder.json");
        String           actual   = marshaller.marshal(border, context);
        Assert.assertEquals(expected.toString(), actual);
    }


    /**
     * Used to test {@link GsonSimpleLineBorderMarshaller#unmarshal(JsonElement, GsonMarshalContext)}.
     *
     * @throws Exception in case of any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        SimpleLineBorder expected = new SimpleLineBorder(ColorConstants.red(), 1.2f);
        JsonObject       json     = readJsonFromFile("org/liquidengine/legui/marshal/json/gsonimpl/border/SimpleLineBorder.json");
        SimpleLineBorder actual   = marshaller.unmarshal(json, context);
        Assert.assertEquals(expected, actual);
    }

}
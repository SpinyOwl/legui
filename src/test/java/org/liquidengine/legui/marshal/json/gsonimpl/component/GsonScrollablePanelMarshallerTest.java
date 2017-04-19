package org.liquidengine.legui.marshal.json.gsonimpl.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.marshal.json.JsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for ScrollablePanel Gson Marshaller.
 */
public class GsonScrollablePanelMarshallerTest extends GsonMarshallingTestBase {
    private ScrollablePanel                                scrollablePanel = new ScrollablePanel(10,30,20,40);
    private GsonScrollablePanelMarshaller<ScrollablePanel> marshaller      = new GsonScrollablePanelMarshaller<>();
    private String                                         pathToJson      = "org/liquidengine/legui/marshal/json/gsonimpl/component/ScrollablePanel.json";

    /**
     * Used to test {@link GsonScrollablePanelMarshaller#marshal(Object, JsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(scrollablePanel, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    /**
     * Used to test {@link GsonScrollablePanelMarshaller#unmarshal(JsonElement, GsonMarshalContext)} method of marshaller.
     *
     * @throws Exception if any exception occurred.
     */
    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        ScrollablePanel     actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(scrollablePanel, actual);
    }
}
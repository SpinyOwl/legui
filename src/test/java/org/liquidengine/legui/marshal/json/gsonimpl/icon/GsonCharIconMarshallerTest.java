package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import com.google.gson.JsonObject;
import org.joml.Vector2f;
import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.font.FontRegistry;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for CharIcon Gson Marshaller.
 */
public class GsonCharIconMarshallerTest extends GsonMarshallingTestBase {

    private CharIcon                         icon       = new CharIcon(new Vector2f(16), FontRegistry.MATERIAL_DESIGN_ICONS, (char) 0x7243);
    private GsonCharIconMarshaller<CharIcon> marshaller = new GsonCharIconMarshaller<>();
    private String                           pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/icon/CharIcon.json";

    @Test
    public void marshal() throws Exception {
        String expected = readJsonFromFile(pathToJson).toString();
        String actual   = marshaller.marshal(icon, new GsonMarshalContext());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void unmarshal() throws Exception {
        JsonObject jsonToUnmarshal = readJsonFromFile(pathToJson);
        CharIcon   actual          = marshaller.unmarshal(jsonToUnmarshal, new GsonMarshalContext());
        Assert.assertEquals(icon, actual);
    }

}
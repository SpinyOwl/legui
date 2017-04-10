package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import org.joml.Vector2f;
import org.junit.Test;
import org.liquidengine.legui.font.FontRegistry;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

/**
 * Test for CharIcon Gson Marshaller.
 */
public class GsonCharIconMarshallerTest extends GsonMarshallingTestBase {

    private CharIcon               icon       = new CharIcon(new Vector2f(16), FontRegistry.MATERIAL_DESIGN_ICONS, (char) 0x7243);
    private GsonCharIconMarshaller marshaller = new GsonCharIconMarshaller();
    private String                 pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/icon/CharIcon.json";

    @Test
    public void marshal() throws Exception {
        testMarshal(icon, marshaller, pathToJson);
    }

    @Test
    public void unmarshal() throws Exception {
        testUnmarshal(icon, marshaller, pathToJson);
    }

}
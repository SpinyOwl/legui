package org.liquidengine.legui.marshal.json.gsonimpl.icon;

import org.joml.Vector2f;
import org.junit.Test;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.loader.ImageLoader;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshallingTestBase;

import static org.junit.Assert.*;

/**
 * Test for ImageIcon Gson Marshaller.
 */
public class GsonImageIconMarshallerTest extends GsonMarshallingTestBase {

    private ImageIcon               icon       = new ImageIcon(new Vector2f(16), ImageLoader.loadImage("test/image.png"));
    private GsonImageIconMarshaller marshaller = new GsonImageIconMarshaller();
    private String                  pathToJson = "org/liquidengine/legui/marshal/json/gsonimpl/icon/ImageIcon.json";

    @Test
    public void marshal() throws Exception {
        testMarshal(icon, marshaller, pathToJson);
    }

    @Test
    public void unmarshal() throws Exception {
        testUnmarshal(icon, marshaller, pathToJson);
    }

}
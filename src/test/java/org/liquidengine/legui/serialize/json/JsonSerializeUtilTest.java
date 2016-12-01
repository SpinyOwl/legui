package org.liquidengine.legui.serialize.json;

import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.util.IOUtil;

/**
 * Created by Alexander on 26.11.2016.
 */
public class JsonSerializeUtilTest {

    private static final String json = IOUtil.loadResourceAsString("org/liquidengine/legui/serialize/json/json.json", 1);

    @Test
    public void serialize() {
        ScrollablePanel panel = new ScrollablePanel(10, 10, 200, 200);
        Slider slider = new Slider(10, 20, 30, 40);
        panel.getContainer().addComponent(slider);

        String json = GsonSerializeUtil.serialize(panel);

        System.out.println(json);
        ScrollablePanel deserialize = GsonSerializeUtil.deserialize(json);

        Assert.assertEquals(panel, deserialize);
    }

    @Test
    public void deserialize() {
        System.out.println(json);
        ScrollablePanel deserialize = GsonSerializeUtil.deserialize(json);
        deserialize.resize();
        deserialize.setSize(600,600);
        deserialize.resize();

        ScrollablePanel panel = new ScrollablePanel(10, 10, 200, 200);
        panel.resize();
        panel.setSize(600,600);
        panel.resize();
//        Slider slider = new Slider(10, 20, 30, 40);
//        panel.getContainer().addComponent(slider);

        Assert.assertEquals(panel, deserialize);
    }
}

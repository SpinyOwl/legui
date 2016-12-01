package org.liquidengine.legui.serialize.json;

import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;
import org.liquidengine.legui.util.IOUtil;

import java.nio.ByteBuffer;

/**
 * Created by Alexander on 26.11.2016.
 */
public class JsonSerializeUtilTest {

    private static final String json = IOUtil.loadResourceAsString("org/liquidengine/legui/serialize/json/Slider.json", 1);

    @Test
    public void serialize() {
        Slider slider = new Slider(10, 20, 30, 40);
        String json = GsonSerializeUtil.serialize(slider);
        System.out.println(json);
        Slider deserialize = GsonSerializeUtil.deserialize(json);
        Assert.assertEquals(slider, deserialize);
    }

    @Test
    public void deserialize() {
        System.out.println(json);
        Slider deserialize = GsonSerializeUtil.deserialize(json);
    }
}

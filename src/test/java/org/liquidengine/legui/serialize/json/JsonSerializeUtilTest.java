package org.liquidengine.legui.serialize.json;

import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.serialize.json.gson.GsonSerializeUtil;

/**
 * Created by Alexander on 26.11.2016.
 */
public class JsonSerializeUtilTest {

    @Test
    public void serialize() {
        Label label = new Label(10, 20, 30, 40, "Hello");
        String json = GsonSerializeUtil.serialize(label);
        System.out.println(json);
        Label deserialize = GsonSerializeUtil.deserialize(json);

        Assert.assertEquals(label.getTextState(), deserialize.getTextState());
        Assert.assertEquals(label, deserialize);
    }
}

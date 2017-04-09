package org.liquidengine.legui.marshal.json.gsonimpl.border;

import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.marshal.json.JsonTestBase;
import org.liquidengine.legui.marshal.json.gsonimpl.GsonMarshalUtil;

/**
 * Created by ShchAlexander on 09.04.2017.
 */
public class GsonSimpleLineBorderMarshallerTest extends JsonTestBase {

    @Ignore
    @Test
    public void test() {
        SimpleLineBorder border    = new SimpleLineBorder(ColorConstants.red(), 1.2f);
        JsonObject       expected  = readJsonFromFile("org/liquidengine/legui/marshal/json/gsonimpl/border/SimpleLineBorder.json");
        SimpleLineBorder unmarshal = GsonMarshalUtil.unmarshal(expected);
        Assert.assertEquals(border, unmarshal);
    }

}
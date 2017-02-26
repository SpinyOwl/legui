package org.liquidengine.legui.marshal.json;

import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalRegistry;

public class TestJsonMarshaller {

    private void testJsonMarshalling(JsonMarshalRegistry registry, JsonMarshalContext context) {
        Button                 b          = new Button(10, 20, 30, 40);
        JsonMarshaller<Button> marshaller = registry.getMarshaller(Button.class);

        String json               = marshaller.marshal(b, context);
        Button unmarshalledButton = marshaller.unmarshal(json, context);

        Assert.assertEquals(b, unmarshalledButton);
    }

    @Test
    public void testGsonImpl() {
        GsonMarshalRegistry registry = GsonMarshalRegistry.getRegistry();
        GsonMarshalContext  context  = new GsonMarshalContext();

        testJsonMarshalling(registry, context);
    }
}

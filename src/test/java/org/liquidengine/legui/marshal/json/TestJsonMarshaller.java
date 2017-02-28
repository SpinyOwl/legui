package org.liquidengine.legui.marshal.json;

import org.junit.Assert;
import org.junit.Test;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.example.ExampleGui;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalContext;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalRegistry;
import org.liquidengine.legui.marshal.json.gsonImpl.GsonMarshalUtil;

public class TestJsonMarshaller {

    private void testJsonMarshalling(JsonMarshalRegistry registry, JsonMarshalContext context) {
        Object toMarshal;
        Object unMarshalled;

        Frame frame = new Frame();
        frame.getContainer().addAll(new ExampleGui().getChilds());

        String marshalled   = GsonMarshalUtil.marshal(frame);
        Frame  unmarshaled  = GsonMarshalUtil.unmarshal(marshalled);
        String remarshalled = GsonMarshalUtil.marshal(unmarshaled);


        toMarshal = frame;
        unMarshalled = unmarshaled;


        String jsonOne = marshalled;
        String jsonTwo = remarshalled;


        System.out.println(jsonOne);
        System.out.println(jsonTwo);
        System.out.println(toMarshal.equals(unMarshalled));

        Assert.assertEquals(toMarshal, unMarshalled);
    }

    @Test
    public void testGsonImpl() {
        GsonMarshalRegistry registry = GsonMarshalRegistry.getRegistry();
        GsonMarshalContext  context  = new GsonMarshalContext();

        testJsonMarshalling(registry, context);
    }
}

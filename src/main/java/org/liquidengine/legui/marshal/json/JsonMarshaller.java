package org.liquidengine.legui.marshal.json;

/**
 * Used to convert Java object to readable json representation and back - json representation to Java object.
 * <p>
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public interface JsonMarshaller<T> {
    String marshal(T object, JsonMarshalContext context);

    T unmarshal(String jsonString, JsonMarshalContext context);
}
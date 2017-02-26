package org.liquidengine.legui.marshal.json;

/**
 * Created by ShchAlexander on 26.02.2017.
 */
public interface JsonMarshalRegistry {

    Class<?> getClassByShortType(String shortName);

    String getShortTypeByClass(Class<?> clazz);

    /**
     * Register marshaller for specified class.
     *
     * @param typeName   short type name, can be null, in that case {@link JsonMarshalRegistry#getShortTypeByClass(Class)} will return null.
     * @param tClass     class.
     * @param marshaller marshaller.
     * @param <T>        type parameter to prevent error in marshal registry.
     */
    <T> void registerMarshaller(String typeName, Class<T> tClass, JsonMarshaller<T> marshaller);

    /**
     * Returns marshaller for specified class
     *
     * @param tClass class
     * @param <T>    type of marshalled/demarshalled class
     * @return json marshaller for specified class
     */
    <T> JsonMarshaller<T> getMarshaller(Class<T> tClass);

    /**
     * Returns marshaller for specified classname
     *
     * @param classname full classname
     * @return json marshaller for specified classname
     */
    JsonMarshaller getMarshaller(String classname);

    /**
     * Returns marshaller for specified type
     *
     * @param typeName type name
     * @return json marshaller for specified type
     */
    JsonMarshaller getMarshallerByShortType(String typeName);
}

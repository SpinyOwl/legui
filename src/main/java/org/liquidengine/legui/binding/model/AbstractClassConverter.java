package org.liquidengine.legui.binding.model;

/**
 * Class converter. Should be used to convert class to string representation and vise versa.
 * <p>
 * Created by ShchAlexander on 02.12.2017.
 */
public abstract class AbstractClassConverter<T> {

    /**
     * Used to convert java object to string representation.
     *
     * @param o object to convert.
     *
     * @return string representation of object.
     */
    public abstract String convertFromJava(T o);

    /**
     * Used to convert string representation to java object.
     *
     * @param o string representation of object to convert.
     *
     * @return object of string representation.
     */
    public abstract T convertToJava(String o);

}

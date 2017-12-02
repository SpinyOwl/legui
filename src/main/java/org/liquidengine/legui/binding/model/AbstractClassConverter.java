package org.liquidengine.legui.binding.model;

/**
 * Created by ShchAlexander on 02.12.2017.
 */
public abstract class AbstractClassConverter<T> {

    public abstract String convertFromJava(T o);

    public abstract T convertToJava(String o);

}

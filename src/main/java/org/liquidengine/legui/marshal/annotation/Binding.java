package org.liquidengine.legui.marshal.annotation;

/**
 * Created by ShchAlexander on 27.10.2017.
 */
public @interface Binding {

    String value();

    Bind[] mappings() default {};
}

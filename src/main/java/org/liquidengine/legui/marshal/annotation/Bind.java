package org.liquidengine.legui.marshal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by ShchAlexander on 27.10.2017.
 */
@Target(ElementType.FIELD)
public @interface Bind {

    String field() default "";

    String value() default "";

}

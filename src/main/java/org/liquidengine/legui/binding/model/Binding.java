package org.liquidengine.legui.binding.model;

/**
 * Created by ShchAlexander on 30.11.2017.
 */
public final class Binding extends AbstractBinding {

    protected Binding(String javaField) {
        super(javaField);
    }

    protected Binding(String javaField, String bindingField) {
        super(javaField, bindingField);
    }
}

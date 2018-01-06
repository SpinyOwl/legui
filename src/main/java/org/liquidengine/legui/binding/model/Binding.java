package org.liquidengine.legui.binding.model;

/**
 * Default binding class.
 * Created by ShchAlexander on 30.11.2017.
 */
public final class Binding extends AbstractBinding {

    /**
     * Constructs binding for specified field name.
     *
     * @param javaFieldName java field name.
     */
    public Binding(String javaFieldName) {
        super(javaFieldName);
    }

    /**
     * Constructs binding for specified java and binding field names.
     *
     * @param javaFieldName java field name.
     * @param bindingFieldName binding field name.
     */
    public Binding(String javaFieldName, String bindingFieldName) {
        super(javaFieldName, bindingFieldName);
    }
}

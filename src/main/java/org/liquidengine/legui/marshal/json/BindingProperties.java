package org.liquidengine.legui.marshal.json;

/**
 * @author Aliaksandr_Shcherbin.
 */
public enum BindingProperties {

    TYPE_PROPERTY("@type"),
    CLASS_PROPERTY("@class");

    private String value;

    private BindingProperties(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

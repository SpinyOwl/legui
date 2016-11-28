package org.liquidengine.legui.serialize.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Alexander on 26.11.2016.
 */
public enum JsonConstants {
    CLASS_PARAMETER("@class");

    private final String value;

    JsonConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}

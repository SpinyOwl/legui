package org.liquidengine.legui.binding.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by ShchAlexander on 30.11.2017.
 */
public final class Binding {
    private String javaField;
    private String bindingField;
    private ClassBinding linkedClassBinding;
    private TargetType targetType;

    public Binding(String javaField) {
        this.javaField = javaField;
    }

    public Binding(String javaField, String bindingField) {
        this.javaField = javaField;
        this.bindingField = bindingField;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public String getBindingField() {
        return bindingField;
    }

    public void setBindingField(String bindingField) {
        this.bindingField = bindingField;
    }

    public ClassBinding getLinkedClassBinding() {
        return linkedClassBinding;
    }

    public void setLinkedClassBinding(ClassBinding linkedClassBinding) {
        this.linkedClassBinding = linkedClassBinding;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("javaField", javaField)
            .append("bindingField", bindingField)
            .append("linkedClassBinding", linkedClassBinding)
            .append("targetType", targetType)
            .toString();
    }
}

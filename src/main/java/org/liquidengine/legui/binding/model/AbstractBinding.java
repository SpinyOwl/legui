package org.liquidengine.legui.binding.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Aliaksandr_Shcherbin.
 */
public abstract class AbstractBinding {

    /**
     * Java field name. During processing first of all search goes through getters and setters and if there is no public getters or setters used direct access
     * to field value.
     */
    private String javaFieldName;

    /**
     * Name of attribute/field in binding representation.
     */
    private String bindingFieldName;

    /**
     * Linked binding which should be used for field conversions.
     */
    private AbstractClassBinding linkedClassBinding;

    /**
     * Linked binding which should be used for field conversions.
     */
    private AbstractClassConverter classConverter;

    /**
     * Target type to which should be converted java field (for example in XML representation it could be field or attribute).
     */
    private TargetType targetType;

    /**
     * Constructs binding for specified field name.
     *
     * @param javaFieldName java field name.
     */
    protected AbstractBinding(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    /**
     * Constructs binding for specified java and binding field names.
     *
     * @param javaFieldName java field name.
     * @param bindingFieldName binding field name.
     */
    protected AbstractBinding(String javaFieldName, String bindingFieldName) {
        this.javaFieldName = javaFieldName;
        this.bindingFieldName = bindingFieldName;
    }

    public String getJavaFieldName() {
        return javaFieldName;
    }

    protected void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    public String getBindingFieldName() {
        return bindingFieldName;
    }

    protected void setBindingFieldName(String bindingFieldName) {
        this.bindingFieldName = bindingFieldName;
    }

    public AbstractClassBinding getLinkedClassBinding() {
        return linkedClassBinding;
    }

    protected void setLinkedClassBinding(AbstractClassBinding linkedClassBinding) {
        this.linkedClassBinding = linkedClassBinding;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    protected void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("javaFieldName", javaFieldName)
            .append("bindingFieldName", bindingFieldName)
            .append("linkedClassBinding", linkedClassBinding)
            .append("targetType", targetType)
            .toString();
    }

    public AbstractClassConverter getClassConverter() {
        return classConverter;
    }

    public void setClassConverter(AbstractClassConverter classConverter) {
        this.classConverter = classConverter;
    }
}

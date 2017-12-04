package org.liquidengine.legui.binding.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;

/**
 * Field binding. Describes how java field should be mapped to external type.
 *
 * @author ShchAlexander.
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
     * Field accessor which coild be used to access field.
     */
    private AbstractFieldAccessor fieldAccessor;

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

    /**
     * Returns java field name.
     *
     * @return java field name.
     */
    public String getJavaFieldName() {
        return javaFieldName;
    }

    /**
     * Used to set java field name.
     *
     * @param javaFieldName java field name to set.
     */
    protected void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    /**
     * Returns binding field name.
     *
     * @return binding field name.
     */
    public String getBindingFieldName() {
        return bindingFieldName;
    }

    /**
     * Used to set binding field name.
     *
     * @param bindingFieldName binding field name to set.
     */
    protected void setBindingFieldName(String bindingFieldName) {
        this.bindingFieldName = bindingFieldName;
    }

    /**
     * Returns linked class binding.
     *
     * @return linked class binding.
     */
    public AbstractClassBinding getLinkedClassBinding() {
        return linkedClassBinding;
    }

    /**
     * Used to set linked class binding.
     *
     * @param linkedClassBinding linked class binding to set.
     */
    protected void setLinkedClassBinding(AbstractClassBinding linkedClassBinding) {
        this.linkedClassBinding = linkedClassBinding;
    }

    /**
     * Returns target type.
     *
     * @return target type.
     */
    public TargetType getTargetType() {
        return targetType;
    }

    /**
     * Used to set target type.
     *
     * @param targetType target type to set.
     */
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

    /**
     * Returns class converter.
     *
     * @return class converter.
     */
    public AbstractClassConverter getClassConverter() {
        return classConverter;
    }

    /**
     * Used to set class converter.
     *
     * @param classConverter class converter to set.
     */
    public void setClassConverter(AbstractClassConverter classConverter) {
        this.classConverter = classConverter;
    }

    public AbstractFieldAccessor getFieldAccessor() {
        return fieldAccessor;
    }

    public void setFieldAccessor(AbstractFieldAccessor fieldAccessor) {
        this.fieldAccessor = fieldAccessor;
    }
}

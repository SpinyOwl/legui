package org.liquidengine.legui.binding.model;

import static org.liquidengine.legui.binding.model.BindingUtilities.classTreeGetFieldType;
import static org.liquidengine.legui.binding.model.BindingUtilities.classTreeHasField;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Binding builder. Used to create instance of class binding.
 *
 * @author ShchAlexander.
 */
public final class BindingBuilder {

    /**
     * Class binding.
     */
    private ClassBinding classBinding;
    /**
     * Bindings.
     */
    private Map<String, Binding> bindings = new HashMap<>();
    /**
     * Inherited bindings.
     */
    private Map<String, Binding> inheritedBindings = new HashMap<>();

    /**
     * Private constructor.
     */
    private BindingBuilder() {
    }

    /**
     * Used to create binding for specified class.
     *
     * @param clazz class to create binding.
     * @param to target binding name.
     *
     * @return builder instance to complete binding creation.
     */
    public static BindingBuilder createForClass(Class clazz, String to) {
        return createForClass(clazz, to, false, null);
    }

    /**
     * Used to create binding for specified class.
     *
     * @param clazz class to create binding.
     * @param to target binding name.
     * @param byDefault should binding used as default for specified class or not
     * @param inherited base class binding.
     *
     * @return builder instance to complete binding creation.
     */
    public static BindingBuilder createForClass(Class clazz, String to, boolean byDefault, ClassBinding inherited) {
        BindingBuilder builder = new BindingBuilder();
        builder.classBinding = new ClassBinding(clazz, byDefault);
        builder.classBinding.setToName(to);
        if (inherited != null) {
            builder.inheritedBindings.putAll(inherited.getBindings());
        }
        return builder;
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field) {
        return bind(field, field, true);
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field, String to) {
        return bind(field, to, true);
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     * @param using binding for specified field.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field, String to, AbstractClassBinding using) {
        return bind(field, to, true, using);
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     * @param attribute target type of binding should be attribute or not.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field, String to, boolean attribute) {
        return bind(field, to, attribute, (AbstractClassBinding) null);
    }

    /**
     * Used to unbind specified field.
     *
     * @param field field to unbind.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder unbind(String field) {
        inheritedBindings.remove(field);
        return this;
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     * @param attribute target type of binding should be attribute or not.
     * @param linkedClassBinding binding for specified field.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field, String to, boolean attribute, AbstractClassBinding linkedClassBinding) {
        checkFieldExist(field);
        checkLinkedBindingFieldTypeIsValid(field, linkedClassBinding);
        Binding binding = createBinding(field, to, attribute, linkedClassBinding);
        binding.setLinkedClassBinding(linkedClassBinding);
        bindings.put(field, binding);
        return this;
    }

    /**
     * Used to bind specified field.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     * @param attribute target type of binding should be attribute or not.
     * @param classConverter class converter for specified field.
     *
     * @return builder instance to complete binding creation.
     */
    public BindingBuilder bind(String field, String to, boolean attribute, AbstractClassConverter classConverter) {
        checkFieldExist(field);
        checkClassConverterType(field, classConverter);
        Binding binding = createBinding(field, to, attribute, null);
        binding.setClassConverter(classConverter);
        bindings.put(field, binding);
        return this;
    }

    /**
     * Used to create binding instance.
     *
     * @param field field to bind.
     * @param to target field name in binding.
     * @param attribute target type of binding should be attribute or not.
     * @param linkedClassBinding binding for specified field.
     *
     * @return binding instance.
     */
    private Binding createBinding(String field, String to, boolean attribute, AbstractClassBinding linkedClassBinding) {
        Binding binding = new Binding(field);
        if (to != null) {
            binding.setBindingFieldName(to);
        } else if (linkedClassBinding != null) {
            binding.setBindingFieldName(linkedClassBinding.getToName());
        } else {
            binding.setBindingFieldName(field);
        }
        if (linkedClassBinding == null) {
            binding.setTargetType(attribute ? TargetType.ATTRIBUTE : TargetType.FIELD);
        } else {
            binding.setTargetType(TargetType.FIELD);
        }
        return binding;
    }

    /**
     * Used to check if class converter type parametrized with right class (class of object field).
     *
     * @param field field name.
     * @param classConverter class converter.
     */
    private void checkClassConverterType(String field, AbstractClassConverter classConverter) {
        Class<? extends AbstractClassConverter> converterClass = classConverter.getClass();
        Type type = converterClass.getGenericSuperclass();
        while (!(type instanceof ParameterizedType) && AbstractClassConverter.class.isAssignableFrom(converterClass)) {
            converterClass = (Class<? extends AbstractClassConverter>) converterClass.getSuperclass();
            type = converterClass.getGenericSuperclass();
        }

        ParameterizedType superclass = (ParameterizedType) type;
        Type[] actualTypeArguments = superclass.getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            throw new BindingCreationException("Can't find converter target type");
        }

        Class bType = (Class) actualTypeArguments[0];
        Class bindingClass = classBinding.getBindingForType();
        Class fieldType = classTreeGetFieldType(bindingClass, field);
        if (bType != fieldType && !bType.isAssignableFrom(fieldType)) {
            throw new BindingCreationException(
                "Field type '" + fieldType.getCanonicalName() + "' is not instance of '" + bType.getCanonicalName() + "'.");
        }

    }

    /**
     * Used to check if linked class binding type parametrized with right class (class of object field).
     *
     * @param field field name.
     * @param linkedClassBinding linked class binding for field.
     */
    private void checkLinkedBindingFieldTypeIsValid(String field, AbstractClassBinding linkedClassBinding) {
        if (linkedClassBinding != null) {
            Class bindingClass = classBinding.getBindingForType();
            Class bType = linkedClassBinding.getBindingForType();
            Class fieldType = classTreeGetFieldType(bindingClass, field);
            if (bType != fieldType && !bType.isAssignableFrom(fieldType)) {
                throw new BindingCreationException(
                    "Field type '" + fieldType.getCanonicalName() + "' is not instance of '" + bType.getCanonicalName() + "'.");
            }
        }
    }

    /**
     * Used to check if field exist in binding class.
     *
     * @param field field name to check.
     */
    private void checkFieldExist(String field) {
        Class bindingClass = classBinding.getBindingForType();
        if (!classTreeHasField(bindingClass, field)) {
            throw new BindingCreationException(
                "Class '" + bindingClass.getCanonicalName() + "' and it's parent classes have not getters/setters or field named as '" + field + "'.");
        }
    }

    /**
     * Creates class binding instance from previously configured steps.
     *
     * @return class binding instance.
     */
    public ClassBinding build() {
        for (Entry<String, Binding> entry : inheritedBindings.entrySet()) {
            classBinding.putBinding(entry.getValue());
        }

        for (Entry<String, Binding> entry : bindings.entrySet()) {
            classBinding.putBinding(entry.getValue());
        }
        return classBinding;
    }

}

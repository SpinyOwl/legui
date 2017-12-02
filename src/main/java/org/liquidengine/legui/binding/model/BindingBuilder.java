package org.liquidengine.legui.binding.model;

import static org.liquidengine.legui.binding.model.BindingUtilities.classTreeGetFieldType;
import static org.liquidengine.legui.binding.model.BindingUtilities.classTreeHasField;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class BindingBuilder {

    private ClassBinding classBinding;
    private Map<String, Binding> bindings = new HashMap<>();
    private Map<String, Binding> inheritedBindings = new HashMap<>();

    private BindingBuilder() {
    }

    public static BindingBuilder createForClass(Class clazz, String to) {
        return createForClass(clazz, to, false, null);
    }

    public static BindingBuilder createForClass(Class clazz, String to, boolean byDefault, ClassBinding inherited) {
        BindingBuilder builder = new BindingBuilder();
        builder.classBinding = new ClassBinding(clazz, byDefault);
        builder.classBinding.setToName(to);
        if (inherited != null) {
            builder.inheritedBindings.putAll(inherited.getBindings());
        }
        return builder;
    }

    public BindingBuilder bind(String field) {
        return bind(field, field, true);
    }

    public BindingBuilder bind(String field, String to) {
        return bind(field, to, true);
    }

    public BindingBuilder bind(String field, String to, AbstractClassBinding using) {
        return bind(field, to, true, using);
    }

    public BindingBuilder bind(String field, String to, boolean attribute) {
        return bind(field, to, attribute, (AbstractClassBinding) null);
    }

    public BindingBuilder unbind(String field) {
        inheritedBindings.remove(field);
        return this;
    }

    public BindingBuilder bind(String field, String to, boolean attribute, AbstractClassBinding linkedClassBinding) {
        checkFieldExist(field);
        checkLinkedBindingFieldTypeIsValid(field, linkedClassBinding);
        Binding binding = createBinding(field, to, attribute, linkedClassBinding);
        binding.setLinkedClassBinding(linkedClassBinding);
        bindings.put(field, binding);
        return this;
    }

    public BindingBuilder bind(String field, String to, boolean attribute, AbstractClassConverter classConverter) {
        checkFieldExist(field);
        checkClassConverterType(field, classConverter);
        Binding binding = createBinding(field, to, attribute, null);
        binding.setClassConverter(classConverter);
        bindings.put(field, binding);
        return this;
    }

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

    private boolean isClassConverter(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return parameterizedType.getRawType().equals(AbstractClassConverter.class);
        }
        return false;
    }

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

    private void checkFieldExist(String field) {
        Class bindingClass = classBinding.getBindingForType();
        if (!classTreeHasField(bindingClass, field)) {
            throw new BindingCreationException(
                "Class '" + bindingClass.getCanonicalName() + "' and it's parent classes have not getters/setters or field named as '" + field + "'.");
        }
    }

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

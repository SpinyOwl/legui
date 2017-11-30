package org.liquidengine.legui.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.binding.model.TargetType;

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
        return bind(field, field, true, null);
    }

    public BindingBuilder bind(String field, String to) {
        return bind(field, to, true, null);
    }

    public BindingBuilder bind(String field, String to, ClassBinding using) {
        return bind(field, to, true, using);
    }

    public BindingBuilder bind(String field, String to, boolean attribute) {
        return bind(field, to, attribute, null);
    }

    public BindingBuilder unbind(String field) {
        inheritedBindings.remove(field);
        return this;
    }

    public BindingBuilder bind(String field, String to, boolean attribute, ClassBinding linkedClassBinding) {
        Binding binding = new Binding(field);
        if (to == null) {
            binding.setBindingField(field);
        } else {
            binding.setBindingField(to);
        }
        if (linkedClassBinding == null) {
            binding.setTargetType(attribute ? TargetType.ATTRIBUTE : TargetType.FIELD);
        } else {
            binding.setTargetType(TargetType.FIELD);
        }
        binding.setLinkedClassBinding(linkedClassBinding);
        bindings.put(field, binding);
        return this;
    }

    public ClassBinding build() {
        for (Entry<String, Binding> entry : inheritedBindings.entrySet()) {
            classBinding.putBinding(entry.getKey(), entry.getValue());
        }

        for (Entry<String, Binding> entry : bindings.entrySet()) {
            classBinding.putBinding(entry.getKey(), entry.getValue());
        }
        return classBinding;
    }

}

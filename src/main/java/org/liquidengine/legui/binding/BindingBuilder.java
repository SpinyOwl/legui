package org.liquidengine.legui.binding;

import java.util.List;
import org.liquidengine.legui.binding.Binding.Bind;
import org.liquidengine.legui.binding.Binding.TargetType;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class BindingBuilder {

    private Binding binding;

    private Class forClass;
    private Binding inherited;
    private List<Bind> bindings;
    private boolean byDefault = false;

    private BindingBuilder() {
    }

    public static BindingBuilder createForClass(Class clazz) {
        BindingBuilder builder = new BindingBuilder();
        builder.binding = new Binding(clazz);
        return builder;
    }

    public BindingBuilder extend(Binding binding) {
        this.inherited = binding;
        return this;
    }

    public BindingBuilder bind(String field) {
        return bind(field, field, true, null);
    }

    public BindingBuilder bind(String field, String to) {
        return bind(field, to, true, null);
    }

    public BindingBuilder bind(String field, String to, Binding using) {
        return bind(field, to, true, using);
    }

    public BindingBuilder bind(String field, String to, boolean attribute) {
        return bind(field, to, attribute, null);
    }

    public BindingBuilder bind(String field, String to, boolean attribute, Binding binding) {
        Bind bind = new Bind(field);
        bind.setBindingField(to);
        bind.setTargetType(attribute ? TargetType.ATTRIBUTE : TargetType.FIELD);
        bind.setLinkedBinding(binding);
        return this;
    }

}

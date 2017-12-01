package org.liquidengine.legui.binding.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Aliaksandr_Shcherbin.
 */
public abstract class AbstractClassBinding<T> {

    /**
     * Class and it's child classes for which could be used this binding.
     */
    private Class<? extends T> bindingForType;

    /**
     * Is this binding should be used by default. (Used mostly during adding this class to {@link org.liquidengine.legui.binding.BindingRegistry
     * BindingRegistry})
     */
    private boolean byDefault;

    /**
     * Default binding name which should be used if other not specified in field binding.
     */
    private String toName;

    /**
     * Field bindings saved as map for easy access by field name.
     */
    private Map<String, Binding> bindings = new HashMap<>();


    /**
     * Constructs class binding.
     *
     * @param bindingForType type for which binding is created.
     * @param byDefault should this binding used as default or not.
     */
    public AbstractClassBinding(Class<? extends T> bindingForType, boolean byDefault) {
        this.bindingForType = bindingForType;
        this.byDefault = byDefault;
    }

    public String getToName() {
        return toName;
    }

    protected void setToName(String toName) {
        this.toName = toName;
    }

    public Class<? extends T> getBindingForType() {
        return bindingForType;
    }

    protected void setBindingForType(Class<? extends T> bindingForType) {
        this.bindingForType = bindingForType;
    }

    protected void putBinding(Binding binding) {
        if (binding != null) {
            bindings.put(binding.getJavaFieldName(), binding);
        }
    }

    public Binding getBinding(String fieldName) {
        if (fieldName != null) {
            return bindings.get(fieldName);
        } else {
            return null;
        }
    }

    public List<Binding> getBindingList() {
        return new ArrayList<>(bindings.values());
    }

    public Map<String, Binding> getBindings() {
        return new HashMap<>(bindings);
    }

    public boolean isByDefault() {
        return byDefault;
    }

    protected void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    public void postConstruct(T instance) {
        // by default doing nothing
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("bindingForType", bindingForType)
            .append("byDefault", byDefault)
            .append("toName", toName)
            .append("bindings", bindings)
            .toString();
    }
}

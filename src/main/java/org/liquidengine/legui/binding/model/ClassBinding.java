package org.liquidengine.legui.binding.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class ClassBinding {

    private Class type;
    private boolean byDefault;
    private String toName;
    private Map<String, Binding> bindings = new HashMap<>();

    public ClassBinding(Class type, boolean byDefault) {
        this.type = type;
        this.byDefault = byDefault;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void putBinding(String field, Binding binding) {
        bindings.put(field, binding);
    }

    public Map<String, Binding> getBindings() {
        return new HashMap<>(bindings);
    }

    public boolean isByDefault() {
        return byDefault;
    }

    public void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("type", type)
            .append("byDefault", byDefault)
            .append("toName", toName)
            .append("bindings", bindings)
            .toString();
    }
}

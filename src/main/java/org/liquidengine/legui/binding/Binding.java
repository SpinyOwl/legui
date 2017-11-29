package org.liquidengine.legui.binding;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class Binding {

    private Class type;
    private boolean byDefault;
    private Map<String, Bind> bindings = new HashMap<>();

    public Binding(Class type, boolean byDefault) {
        this.type = type;
        this.byDefault = byDefault;
    }

    public Binding(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void bind(String field, Bind bind) {
        bindings.put(field, bind);
    }

    public Map<String, Bind> getBindings() {
        return new HashMap<>(bindings);
    }

    public boolean isByDefault() {
        return byDefault;
    }

    public void setByDefault(boolean byDefault) {
        this.byDefault = byDefault;
    }

    public enum TargetType {
        FIELD, ATTRIBUTE
    }

    public static final class Bind {

        private String javaField;
        private String bindingField;
        private Binding linkedBinding;
        private TargetType targetType;

        public Bind(String javaField) {
            this.javaField = javaField;
        }

        public Bind(String javaField, String bindingField) {
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

        public Binding getLinkedBinding() {
            return linkedBinding;
        }

        public void setLinkedBinding(Binding linkedBinding) {
            this.linkedBinding = linkedBinding;
        }

        public TargetType getTargetType() {
            return targetType;
        }

        public void setTargetType(TargetType targetType) {
            this.targetType = targetType;
        }
    }
}

package org.liquidengine.legui.binding;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class Binding {

    private String type;
    private boolean byDefault;

    public Binding(String type, boolean byDefault) {
        this.type = type;
        this.byDefault = byDefault;
    }

    public Binding(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        private String linkedBinding;
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

        public String getLinkedBinding() {
            return linkedBinding;
        }

        public void setLinkedBinding(String linkedBinding) {
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

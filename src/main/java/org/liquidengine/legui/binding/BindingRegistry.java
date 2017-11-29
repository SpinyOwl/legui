package org.liquidengine.legui.binding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShchAlexander on 29.11.2017.
 */
public final class BindingRegistry {

    private Map<Class, Binding> bindingMap = new HashMap<>();

    /**
     * Private constructor
     */
    private BindingRegistry() {
    }

    /**
     * Returns binding registry instance.
     *
     * @return binding registry instance.
     */
    public static BindingRegistry getInstance() {
        return BRH.INSTANCE;
    }

    public void loadBindings(String bindingListPath) {

    }

    public void setBinding(Class c, Binding b) {
        if (c != null) {
            if (b != null && (b.getType() != c && !b.getType().isAssignableFrom(c))) {
                System.out.println("Can't add binding");
                return;
            }
            bindingMap.put(c, b);
        }
    }

    public Binding getBinding(Class c) {
        return cycledSearch(c);
    }

    protected Binding cycledSearch(Class clazz) {
        Binding binding = null;
        Class cClass = clazz;
        while (binding == null) {
            binding = bindingMap.get(cClass);
            if (cClass.isAssignableFrom(Object.class)) {
                break;
            }
            cClass = cClass.getSuperclass();
        }
        return binding;
    }

    /**
     * Singleton "On demand holder"
     */
    private static class BRH {

        /**
         * BindingRegistry instance.
         */
        private static final BindingRegistry INSTANCE = new BindingRegistry();
    }

}

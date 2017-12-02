package org.liquidengine.legui.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.binding.parser.BindingParserService;

/**
 * Created by ShchAlexander on 29.11.2017.
 */
public final class BindingRegistry {

    /**
     * Used to hold default bindings.
     */
    private Map<Class, ClassBinding> bindingMap = new HashMap<>();

    /**
     * Private constructor.
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

    /**
     * Used to load bindings from binding list.
     *
     * @param bindingListPath path to binding list.
     */
    public void loadBindings(String bindingListPath) {
        Map<Class, ClassBinding> map = BindingParserService.getInstance().parseList(bindingListPath);
        if (map != null) {
            for (Entry<Class, ClassBinding> entry : map.entrySet()) {
                setBinding(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Used to set specified binding for specified class.
     * If binding is not set as default ({@link ClassBinding#byDefault}) it will not be added.
     *
     * @param c class to bind.
     * @param b binding for class.
     * @param <T> class type.
     */
    public <T> void setBinding(Class<? extends T> c, ClassBinding<? extends T> b) {
        if (c != null) {
            if (b != null) {
                Class<? extends T> type = b.getBindingForType();
                if (type != c && !type.isAssignableFrom(c)) {
                    System.out.println("Can't add binding. Class '" + c.getCanonicalName() + "' is not instance of '" + type.getCanonicalName() + "'.");
                    return;
                }
                if (!b.isByDefault()) {
                    System.out.println("Can't add binding for class '" + c.getCanonicalName() + "' and binding type '" + type.getCanonicalName()
                        + "' cause it is not default for this type.");
                    return;
                }
                bindingMap.put(c, b);
            }
        }
    }

    /**
     * Used to retrieve binding for specified class.
     * If there is no binding for this concrete class it will search binding for all of it's superclasses.
     *
     * @param c class to search binding.
     * @param <T> class type.
     *
     * @return returns default binding for class or null.
     */
    public <T> ClassBinding<T> getBinding(Class<T> c) {
        return cycledSearch(c);
    }

    /**
     * Returns all bindings as map.
     *
     * @return all bindings as map.
     */
    public Map<Class, ClassBinding> getBindingMap() {
        return new HashMap<>(bindingMap);
    }

    /**
     * Used to search binding for class through all class hierarchy of specified class.
     *
     * @param clazz class to search binding.
     * @param <T> type of class.
     *
     * @return binding or null if not found.
     */
    protected <T> ClassBinding<T> cycledSearch(Class<T> clazz) {
        ClassBinding classBinding = null;
        Class cClass = clazz;

        if (clazz == null) {
            return null;
        }
        while (classBinding == null && cClass != null) {
            classBinding = bindingMap.get(cClass);
            if (cClass.isAssignableFrom(Object.class)) {
                break;
            }
            cClass = cClass.getSuperclass();
        }
        return classBinding;
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

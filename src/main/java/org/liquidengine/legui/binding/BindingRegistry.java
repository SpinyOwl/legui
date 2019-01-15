package org.liquidengine.legui.binding;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.binding.parser.BindingParserService;

/**
 * Binding registry. Holds bindings mapped to class.
 * <p>
 * Created by ShchAlexander on 29.11.2017.
 */
public final class BindingRegistry {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Used to hold default bindings.
     */
    private Map<Class, AbstractClassBinding> bindingMap = new LinkedHashMap<>();
    /**
     * Used to hold default bindings.
     */
    private Map<String, AbstractClassBinding> bindingMapByTypeAlias = new LinkedHashMap<>();

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
        Map<Class, AbstractClassBinding> map = BindingParserService.getInstance().parseList(bindingListPath);
        if (map != null) {
            for (Entry<Class, AbstractClassBinding> entry : map.entrySet()) {
                Class c = entry.getKey();
                AbstractClassBinding b = entry.getValue();
                if (bindingIsNotForType(c, b)) {
                    addBinding(entry.getValue());
                    return;
                } else {
                    addDefaultBinding(c, b);
                }
            }
        }
    }

    /**
     * Checks if binding can be used with specified type.
     *
     * @param c class.
     * @param b binding.
     *
     * @return true if binding can't be used with specified type.
     */
    private boolean bindingIsNotForType(Class c, AbstractClassBinding b) {
        if (c != null && b != null) {
            Class type = b.getBindingForType();
            if (type != c && !type.isAssignableFrom(c)) {
                LOGGER.warn("Can't add binding. Class '" + c.getCanonicalName() + "' is not instance of '" + type.getCanonicalName() + "'.");
                return true;
            }
        }
        return false;
    }

    /**
     * Used to set specified binding for specified class. If binding is not set as default ({@link ClassBinding#isByDefault()} it will not be added.
     *
     * @param c class to bind.
     * @param b binding for class.
     * @param <T> class type.
     */
    public <T> void setDefaultBinding(Class<? extends T> c, AbstractClassBinding<? extends T> b) {
        if (c != null && b != null) {
            if (bindingIsNotForType(c, b)) {
                return;
            }
            addDefaultBinding(c, b);
        }
    }

    /**
     * Used to add specified binding for specified class (without type check). If binding is not set as default ({@link ClassBinding#isByDefault()}) it will not be
     * added.
     *
     * @param c class to bind.
     * @param b binding for class.
     * @param <T> class type.
     */
    private <T> void addDefaultBinding(Class<? extends T> c, AbstractClassBinding<? extends T> b) {
        if (!b.isByDefault()) {
            LOGGER.warn("Can't add binding for class '" + c.getCanonicalName()
                            + "' and binding type '" + b.getBindingForType().getCanonicalName()
                            + "' cause it is not default for this type.");
            return;
        }
        bindingMap.put(c, b);
        bindingMapByTypeAlias.put(b.getToName(), b);
    }

    /**
     * Used to add class binding to alias binding registry.
     *
     * @param b binding for class.
     */
    public void addBinding(AbstractClassBinding b) {
        if (b != null) {
            if (b.isByDefault()) {
                setDefaultBinding(b.getBindingForType(), b);
            } else {
                bindingMapByTypeAlias.put(b.getToName(), b);
            }
        }
    }

    /**
     * Used to retrieve binding for specified class. If there is no binding for this concrete class it will search binding for all of it's superclasses.
     *
     * @param c class to search binding.
     * @param <T> class type.
     *
     * @return returns default binding for class or null.
     */
    public <T> AbstractClassBinding<T> getBinding(Class<T> c) {
        return cycledSearch(c);
    }

    /**
     * Used to retrieve binding for specified type alias.
     *
     * @param alias class alias.
     *
     * @return returns binding for alias or null.
     */
    public AbstractClassBinding getBindingByTypeAlias(String alias) {
        return bindingMapByTypeAlias.get(alias);
    }

    /**
     * Returns all default bindings as map.
     *
     * @return all default bindings as map.
     */
    public Map<Class, AbstractClassBinding> getDefaultBindingMap() {
        return new LinkedHashMap<>(bindingMap);
    }

    /**
     * Returns all alias bindings as map.
     *
     * @return all alias bindings as map.
     */
    public Map<String, AbstractClassBinding> getAliasBindingMap() {
        return new LinkedHashMap<>(bindingMapByTypeAlias);
    }

    /**
     * Used to search binding for class through all class hierarchy of specified class.
     *
     * @param clazz class to search binding.
     * @param <T> type of class.
     *
     * @return binding or null if not found.
     */
    protected <T> AbstractClassBinding<T> cycledSearch(Class<T> clazz) {
        AbstractClassBinding classBinding = null;
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
     * Singleton "On demand holder".
     */
    private static class BRH {

        /**
         * BindingRegistry instance.
         */
        private static final BindingRegistry INSTANCE = new BindingRegistry();
    }

}

package org.liquidengine.legui.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.ClassBinding;
import org.liquidengine.legui.binding.parser.BindingParserService;

/**
 * Created by ShchAlexander on 29.11.2017.
 */
public final class BindingRegistry {

    private Map<Class, ClassBinding> bindingMap = new HashMap<>();

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
        Map<Class, ClassBinding> map = BindingParserService.getInstance().parseList(bindingListPath);
        if (map != null) {
            for (Entry<Class, ClassBinding> entry : map.entrySet()) {
                setBinding(entry.getKey(), entry.getValue());
            }
        }
    }

    public void setBinding(Class c, ClassBinding b) {
        if (c != null) {
            if (b != null) {
                if (b.getType() != c && !b.getType().isAssignableFrom(c)) {
                    System.out.println("Can't add binding. Class " + c + " is not instance of " + b.getType());
                    return;
                }
                if (!b.isByDefault()) {
                    System.out.println("Can't add binding for class " + c + " and binding type " + b.getType() + " cause it is not default for this type.");
                    return;
                }
                bindingMap.put(c, b);
            }
        }
    }

    public ClassBinding getBinding(Class c) {
        return cycledSearch(c);
    }

    public Map<Class, ClassBinding> getBindingMap() {
        return new HashMap<>(bindingMap);
    }

    protected ClassBinding cycledSearch(Class clazz) {
        ClassBinding classBinding = null;
        Class cClass = clazz;
        while (classBinding == null) {
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

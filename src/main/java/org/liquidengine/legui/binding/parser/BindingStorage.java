package org.liquidengine.legui.binding.parser;

import java.util.HashMap;
import java.util.Map;
import org.liquidengine.legui.binding.model.ClassBinding;

/**
 * Internal binding storage. Used only by parsers.
 * <p>
 * Created by ShchAlexander on 30.11.2017.
 */
class BindingStorage {

    /**
     * Bindings map.
     */
    private Map<String, ClassBinding> bindingsByPath = new HashMap<>();

    /**
     * Private constructor.
     */
    private BindingStorage() {
    }

    /**
     * Returns binding storage instance.
     *
     * @return binding storage instance.
     */
    public static BindingStorage getInstance() {
        return BSH.INSTANCE;
    }

    /**
     * Returns binding by binding path.
     *
     * @param path path to get binding.
     *
     * @return binding by binding path.
     */
    protected ClassBinding getBinding(String path) {
        return bindingsByPath.get(path);
    }

    /**
     * Used to add binding to storage.
     *
     * @param path binding path.
     * @param binding binding.
     */
    protected void putBinding(String path, ClassBinding binding) {
        bindingsByPath.put(path, binding);
    }

    /**
     * Singleton "on demand holder".
     */
    private static class BSH {

        /**
         * Singleton instance "on demand holder".
         */
        private static final BindingStorage INSTANCE = new BindingStorage();
    }
}

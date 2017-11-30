package org.liquidengine.legui.binding.parser;

import java.util.HashMap;
import java.util.Map;
import org.liquidengine.legui.binding.model.ClassBinding;

/**
 * Created by ShchAlexander on 30.11.2017.
 */
class BindingStorage {

    private Map<String, ClassBinding> bindingsByPath = new HashMap<>();

    private BindingStorage() {
    }

    public static BindingStorage getInstance() {
        return BSH.INSTANCE;
    }

    ClassBinding getBinding(String path) {
        return bindingsByPath.get(path);
    }

    void putBinding(String path, ClassBinding binding) {
        bindingsByPath.put(path, binding);
    }

    private static class BSH {

        private static final BindingStorage INSTANCE = new BindingStorage();
    }
}

package org.liquidengine.legui.binding;

import java.util.Map;

/**
 * Created by ShchAlexander on 29.11.2017.
 */
public final class BindingRegistry {


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

    public Binding getBinding(Class c) {
        return null;
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

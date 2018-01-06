package org.liquidengine.legui.binding.model;

/**
 * Default binding implementation.
 *
 * @param <T> type of class binding.
 *
 * @author ShchAlexander.
 */
public final class ClassBinding<T> extends AbstractClassBinding<T> {

    /**
     * Constructs class binding.
     *
     * @param bindingForType type for which binding is created.
     * @param toName name which should be used as default element name.
     * @param byDefault should this binding used as default or not.
     */
    public ClassBinding(Class<? extends T> bindingForType, String toName, boolean byDefault) {
        super(bindingForType, toName, byDefault);
    }
}

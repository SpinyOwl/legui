package org.liquidengine.legui.binding.custom.util;

import java.util.List;
import org.liquidengine.legui.binding.model.AbstractClassBinding;

/**
 * Created by ShchAlexander on 05.12.2017.
 */
public class ListBinding extends AbstractClassBinding<List> {

    /**
     * Constructs class binding.
     *
     * @param bindingForType type for which binding is created.
     * @param byDefault should this binding used as default or not.
     */
    public ListBinding(Class<? extends List> bindingForType, boolean byDefault) {
        super(bindingForType, byDefault);
    }


}

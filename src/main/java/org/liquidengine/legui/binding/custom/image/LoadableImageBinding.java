package org.liquidengine.legui.binding.custom.image;

import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.image.LoadableImage;

/**
 * Created by ShchAlexander on 01.12.2017.
 */
public class LoadableImageBinding extends AbstractClassBinding<LoadableImage> {

    /**
     * Constructs class binding.
     *
     * @param bindingForType type for which binding is created.
     * @param byDefault should this binding used as default or not.
     */
    public LoadableImageBinding(Class<? extends LoadableImage> bindingForType, boolean byDefault) {
        super(bindingForType, byDefault);
    }

    @Override
    public void postConstruct(LoadableImage instance) {
        instance.load();
    }
}

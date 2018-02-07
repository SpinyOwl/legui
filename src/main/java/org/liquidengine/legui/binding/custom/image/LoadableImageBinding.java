package org.liquidengine.legui.binding.custom.image;

import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.TargetType;
import org.liquidengine.legui.image.LoadableImage;

/**
 * Binding for {@link LoadableImage} instances. <p> Created by ShchAlexander on 01.12.2017.
 */
public class LoadableImageBinding extends AbstractClassBinding<LoadableImage> {

    /**
     * Constructs class binding.
     */
    public LoadableImageBinding() {
        super(LoadableImage.class, LoadableImage.class.getSimpleName().toLowerCase(), true);

        Binding binding = new Binding("path");
        binding.setTargetType(TargetType.ATTRIBUTE);
        putBinding(binding);
    }

    /**
     * Used to load image after construction.
     *
     * @param instance instance to operate.
     */
    @Override
    public void postConstruct(LoadableImage instance) {
        instance.load();
    }
}

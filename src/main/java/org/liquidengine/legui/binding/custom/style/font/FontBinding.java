package org.liquidengine.legui.binding.custom.style.font;

import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;
import org.liquidengine.legui.binding.model.TargetType;
import org.liquidengine.legui.style.font.Font;

/**
 * Created by ShchAlexander on 03.01.2018.
 */
public class FontBinding extends AbstractClassBinding<Font> {

    /**
     * Constructs class binding.
     */
    public FontBinding() {
        super(Font.class, "font", true);

        Binding binding = new Binding("path");
        binding.setTargetType(TargetType.ATTRIBUTE);
        putBinding(binding);
    }

    /**
     * Used to load font after constructing and filling instance.
     *
     * @param instance instance to operate.
     */
    @Override
    public void postConstruct(Font instance) {
        super.postConstruct(instance);
        instance.load();
    }
}

package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.LayerContainer;

/**
 * White LayerContainer Theme for all layer containers. Used to make layer container white.
 *
 * @param <T> {@link LayerContainer} subclasses.
 */
public class WhiteLayerContainerTheme<T extends LayerContainer> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
    }
}

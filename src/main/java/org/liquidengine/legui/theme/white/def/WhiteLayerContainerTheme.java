package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.LayerContainer;

/**
 * White LayerContainer Theme for all layer containers. Used to make layer container white.
 *
 * @param <T> {@link LayerContainer} subclasses.
 */
public class WhiteLayerContainerTheme<T extends LayerContainer> extends WhiteContainerTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(null);
        component.setBackgroundColor(ColorConstants.transparent());
    }
}

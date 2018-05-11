package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark LayerContainer Theme for all layer containers. Used to make layer container dark.
 *
 * @param <T> {@link LayerContainer} subclasses.
 */
public class FlatColoredLayerContainerTheme<T extends LayerContainer> extends FlatColoredComponentTheme<T> {

    public FlatColoredLayerContainerTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
    }
}

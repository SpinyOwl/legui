package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.LayerContainer;

/**
 * Dark LayerContainer Theme for all layer containers. Used to make layer container dark.
 *
 * @param <T> {@link LayerContainer} subclasses.
 */
public class DarkLayerContainerTheme<T extends LayerContainer> extends DarkContainerTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(null);
        component.setBackgroundColor(ColorConstants.transparent());
    }
}

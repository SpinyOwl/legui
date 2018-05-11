package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark SelectBoxScrollablePanel Theme.
 *
 * @param <T> {@link SelectBox.SelectBoxScrollablePanel} subclasses.
 */
public class FlatColoredSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends FlatColoredScrollablePanelTheme<T> {

    public FlatColoredSelectBoxScrollablePanelTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
    }

    @Override
    public void apply(T component) {
        super.apply(component);

        component.getStyle().getBackground().setColor(ColorConstants.red());
    }
}

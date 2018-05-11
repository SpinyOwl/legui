package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;

/**
 * Dark SelectBoxScrollablePanel Theme.
 *
 * @param <T> {@link SelectBox.SelectBoxScrollablePanel} subclasses.
 */
public class FlatSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends FlatScrollablePanelTheme<T> {

    private final Vector4f backgroundColor;

    public FlatSelectBoxScrollablePanelTheme(Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor, Vector4f allowColor,
        Vector4f denyColor) {
        super(backgroundColor, borderColor, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void apply(T component) {
        super.apply(component);

        component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, ColorUtil.oppositeBlackOrWhite(backgroundColor).mul(0.8f)));

        component.getStyle().getBackground().setColor(ColorConstants.red());
    }
}

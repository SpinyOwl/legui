package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * White Label Theme for all labels. Used to make label white.
 *
 * @param <T> {@link Label} subclasses.
 */
public class WhiteLabelTheme<T extends Label> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorConstants.black());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

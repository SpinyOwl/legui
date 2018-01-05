package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.SelectBox;

/**
 * White SelectBox Theme for all select boxes. Used to make select box white.
 *
 * @param <T> {@link SelectBox.SelectBoxElement} subclasses.
 */
public class WhiteSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorConstants.black());
    }
}

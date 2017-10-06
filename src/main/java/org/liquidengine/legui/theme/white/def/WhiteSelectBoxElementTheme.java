package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.SelectBox;

/**
 * White SelectBox Theme for all select boxes. Used to make select box white.
 *
 * @param <T> {@link SelectBox.SelectBoxElement} subclasses.
 */
public class WhiteSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends WhiteControllerTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(null);
        component.setBackgroundColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorConstants.black());
    }
}

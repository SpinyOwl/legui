package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox.SelectBoxElement} subclasses.
 */
public class DarkSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends DarkComponentTheme<T> {

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
        component.getTextState().setTextColor(ColorConstants.white());
    }
}

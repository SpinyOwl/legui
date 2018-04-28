package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class DarkButtonTheme<T extends Button> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
        component.getStyle().getBackground().setColor(ColorConstants.darkGray());
        component.getHoveredStyle().getBackground().setColor(ColorConstants.darkGray().mul(1.5f,1.5f,1.5f,1));
        component.getPressedStyle().getBackground().setColor(ColorConstants.darkGray().mul(2.5f,2.5f,2.5f,1));
        component.getTextState().setTextColor(ColorConstants.white());
    }
}

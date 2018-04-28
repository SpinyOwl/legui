package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * White Button Theme for all buttons. Used to make button white.
 *
 * @param <T> {@link Button} subclasses.
 */
public class WhiteButtonTheme<T extends Button> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(ColorConstants.white());
        component.getHoveredStyle().getBackground().setColor(ColorConstants.lightGray());
        component.getPressedStyle().getBackground().setColor(ColorConstants.gray());
        component.getTextState().setTextColor(ColorConstants.black());
    }
}

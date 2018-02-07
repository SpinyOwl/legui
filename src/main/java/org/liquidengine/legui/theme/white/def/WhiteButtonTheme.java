package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.Button;

/**
 * White Button Theme for all buttons. Used to make button white.
 *
 * @param <T> {@link Button} subclasses.
 */
public class WhiteButtonTheme<T extends Button> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getTextState().setTextColor(ColorConstants.black());
    }
}

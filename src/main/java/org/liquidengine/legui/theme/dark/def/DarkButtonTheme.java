package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Button;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 */
public class DarkButtonTheme<T extends Button> extends DarkControllerTheme<T> {
    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.setBorder(new SimpleLineBorder(ColorConstants.lightGray(), 1.2f));
        component.getTextState().setTextColor(ColorConstants.white());
    }
}

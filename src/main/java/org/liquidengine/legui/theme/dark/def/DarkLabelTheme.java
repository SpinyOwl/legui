package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * Dark Label Theme for all labels. Used to make label dark.
 *
 * @param <T> {@link Label} subclasses.
 */
public class DarkLabelTheme<T extends Label> extends DarkControllerTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setBorder(null);
        component.setBackgroundColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorConstants.white());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

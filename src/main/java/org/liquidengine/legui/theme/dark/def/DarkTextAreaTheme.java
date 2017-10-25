package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * Dark TextArea Theme for all text areas. Used to make text area dark.
 *
 * @param <T> {@link TextArea} subclasses.
 */
public class DarkTextAreaTheme<T extends TextArea> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getTextState().setTextColor(ColorConstants.white());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(ColorConstants.black());
    }
}

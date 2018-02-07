package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * White TextArea Theme for all text areas. Used to make text area white.
 *
 * @param <T> {@link TextArea} subclasses.
 */
public class WhiteTextAreaTheme<T extends TextArea> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getTextState().setTextColor(ColorConstants.black());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(ColorConstants.lightBlue());
    }
}

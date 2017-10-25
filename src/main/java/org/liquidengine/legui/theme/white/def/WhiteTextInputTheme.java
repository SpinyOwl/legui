package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;

/**
 * White TextInput Theme for all text inputs. Used to make text input white.
 *
 * @param <T> {@link TextInput} subclasses.
 */
public class WhiteTextInputTheme<T extends TextInput> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getTextState().setTextColor(ColorConstants.black());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(ColorConstants.lightBlue());
    }
}

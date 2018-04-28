package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * White TextInput Theme for all text inputs. Used to make text input white.
 *
 * @param <T> {@link TextInput} subclasses.
 */
public class WhiteTextInputTheme<T extends TextInput> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getFocusedStyle().getBackground().setColor(ColorConstants.white().mul(.9f, .9f, .9f, 1f));
        component.getTextState().setTextColor(ColorConstants.black());
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHighlightColor(ColorConstants.lightBlue());
    }
}

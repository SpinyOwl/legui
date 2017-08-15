package org.liquidengine.legui.theme.dark.def;

import static org.liquidengine.legui.font.FontRegistry.MATERIAL_ICONS_REGULAR;

import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.icon.CharIcon;

/**
 * Dark CheckBox Theme for all check boxes. Used to make check box dark.
 *
 * @param <T> {@link CheckBox} subclasses.
 */
public class DarkCheckBoxTheme<T extends CheckBox> extends DarkControllerTheme<T> {

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
        component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE835, ColorConstants.white()));
        component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE834, ColorConstants.white()));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

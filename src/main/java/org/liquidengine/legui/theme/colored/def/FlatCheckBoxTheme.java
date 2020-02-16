package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector2f;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

import static org.liquidengine.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Dark CheckBox Theme for all check boxes. Used to make check box dark.
 *
 * @param <T> {@link CheckBox} subclasses.
 */
public class FlatCheckBoxTheme<T extends CheckBox> extends FlatBorderlessTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatCheckBoxTheme() {
    }

    public FlatCheckBoxTheme(FlatColoredThemeSettings settings) {
        super(settings);
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.setIconUnchecked(
            new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE835, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component
            .setIconChecked(new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE834, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

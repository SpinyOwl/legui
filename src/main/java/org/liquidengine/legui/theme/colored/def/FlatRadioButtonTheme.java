package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector2f;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

import static org.liquidengine.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

/**
 * Dark RadioButton Theme for all radio buttons. Used to make radio button dark.
 *
 * @param <T> {@link RadioButton} subclasses.
 */
public class FlatRadioButtonTheme<T extends RadioButton> extends FlatBorderlessTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatRadioButtonTheme() {
    }

    public FlatRadioButtonTheme(FlatColoredThemeSettings settings) {
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
        component.getStyle().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
        component.setIconUnchecked(
            new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE836, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component
            .setIconChecked(new CharIcon(new Vector2f(14), MATERIAL_ICONS_REGULAR, (char) 0xE837, ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getStyle().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

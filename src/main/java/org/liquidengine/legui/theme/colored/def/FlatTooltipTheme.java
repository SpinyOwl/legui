package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Tooltip Theme for all tooltips. Used to make tooltip dark.
 *
 * @param <T> tooltip subclasses.
 */
public class FlatTooltipTheme<T extends Tooltip> extends FlatComponentTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatTooltipTheme() {
    }

    public FlatTooltipTheme(FlatColoredThemeSettings settings) {
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
        Vector4f bgc = ColorUtil.oppositeBlackOrWhite(settings.backgroundColor());
        component.getStyle().getBackground().setColor(bgc);
        Vector4f tc = ColorUtil.oppositeBlackOrWhite(bgc);
        component.getStyle().setTextColor(tc);
        component.getStyle().setShadow(new Shadow(1, 1, 16, -4, tc.mul(0.8f, new Vector4f())));
    }
}

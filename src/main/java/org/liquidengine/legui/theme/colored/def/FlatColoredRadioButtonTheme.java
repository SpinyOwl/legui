package org.liquidengine.legui.theme.colored.def;

import static org.liquidengine.legui.style.font.FontRegistry.MATERIAL_ICONS_REGULAR;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;

/**
 * Dark RadioButton Theme for all radio buttons. Used to make radio button dark.
 *
 * @param <T> {@link RadioButton} subclasses.
 */
public class FlatColoredRadioButtonTheme<T extends RadioButton> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;

    public FlatColoredRadioButtonTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(null);
        component.getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor));
        component.setIconUnchecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE836, ColorUtil.oppositeBlackOrWhite(backgroundColor)));
        component.setIconChecked(new CharIcon(new Vector2f(16), MATERIAL_ICONS_REGULAR, (char) 0xE837, ColorUtil.oppositeBlackOrWhite(backgroundColor)));
        component.getIconUnchecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getIconChecked().setHorizontalAlign(HorizontalAlign.LEFT);
        component.getTextState().setHorizontalAlign(HorizontalAlign.LEFT);
    }
}

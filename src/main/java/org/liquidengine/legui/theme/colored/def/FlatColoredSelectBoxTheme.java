package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector4f;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox} subclasses.
 */
public class FlatColoredSelectBoxTheme<T extends SelectBox> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f backgroundColor2;

    public FlatColoredSelectBoxTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.backgroundColor2 = backgroundColor2;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(backgroundColor2);

        component.getExpandButton().getStyle().setBorder(null);
        component.getExpandButton().getStyle().getBackground().setColor(ColorConstants.transparent());

        component.getSelectionButton().getStyle().setBorder(null);
        component.getSelectionButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getSelectionButton().getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));

        Icon collapseIcon = component.getCollapseIcon();
        if (collapseIcon != null && collapseIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) collapseIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }
        Icon expandIcon = component.getExpandIcon();
        if (expandIcon != null && expandIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) expandIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);

        Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
    }
}

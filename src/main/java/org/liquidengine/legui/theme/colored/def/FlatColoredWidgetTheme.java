package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark Widget Theme for all scrollable widgets. Used to make widget dark.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class FlatColoredWidgetTheme<T extends Widget> extends FlatColoredComponentTheme<T> {

    private final Vector4f backgroundColor;
    private final Vector4f backgroundColor2;
    private final Vector4f strokeColor;
    private final Vector4f allowColor;
    private final Vector4f denyColor;

    public FlatColoredWidgetTheme(Vector4f backgroundColor, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(backgroundColor, backgroundColor2, strokeColor, allowColor, denyColor);
        this.backgroundColor = backgroundColor;
        this.backgroundColor2 = backgroundColor2;
        this.strokeColor = strokeColor;
        this.allowColor = allowColor;
        this.denyColor = denyColor;
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        Vector4f bgc = ColorUtil.oppositeBlackOrWhite(new Vector4f(backgroundColor).mul(3)).add(new Vector4f(backgroundColor).mul(3)).div(4);

        component.getStyle().getBackground().setColor(bgc);

        component.getMinimizeButton().getStyle().getBackground().setColor(new Vector4f(allowColor));
        component.getMinimizeButton().getHoveredStyle().getBackground().setColor(new Vector4f(allowColor).add(ColorUtil.oppositeBlackOrWhite(allowColor)).div(2));
        component.getMinimizeButton().getStyle().setBorder(null);
        component.getMinimizeButton().getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(allowColor));

        component.getCloseButton().getStyle().getBackground().setColor(new Vector4f(denyColor));
        component.getCloseButton().getHoveredStyle().getBackground().setColor(new Vector4f(denyColor).add(ColorUtil.oppositeBlackOrWhite(allowColor)).div(2));
        component.getCloseButton().getStyle().setBorder(null);
        component.getCloseButton().getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(denyColor));

        component.getTitleContainer().getStyle().getBackground().setColor(new Vector4f(backgroundColor2));

        component.getResizeButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getHoveredStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getPressedStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getStyle().setBorder(null);

        CharIcon icon = new CharIcon(FontRegistry.MATERIAL_DESIGN_ICONS, '\uF45D');
        icon.setSize(new Vector2f(20, 20));
        icon.setPosition(new Vector2f(-10, -10));
        icon.setColor(ColorUtil.oppositeBlackOrWhite(bgc));
        component.getResizeButton().getStyle().getBackground().setIcon(icon);

        TextState titleTextState = component.getTitleTextState();
        titleTextState.setPadding(7, 3, 5, 2);
        titleTextState.setTextColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
        Icon closeIcon = component.getCloseIcon();
        if (closeIcon != null && closeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) closeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon minimizeIcon = component.getMinimizeIcon();
        if (minimizeIcon != null && minimizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) minimizeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon maximizeIcon = component.getMaximizeIcon();
        if (maximizeIcon != null && maximizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) maximizeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(backgroundColor2));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getContainer());
        component.getContainer().getStyle().getBackground().setColor(new Vector4f(bgc));
        component.getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
        component.getTitleContainer().getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
        component.getContainer().getStyle().setBorder(new SimpleLineBorder(new Vector4f(backgroundColor2), 1));
    }
}

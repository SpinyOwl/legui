package org.liquidengine.legui.theme.colored.def;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
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
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Widget Theme for all scrollable widgets. Used to make widget dark.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class FlatWidgetTheme<T extends Widget> extends FlatComponentTheme<T> {

    private final FlatColoredThemeSettings settings;

    public FlatWidgetTheme(FlatColoredThemeSettings settings) {
        super(settings);
        this.settings = settings;
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        component.getStyle().getBackground().setColor(settings.backgroundColor());

        if (settings.shadowColor() == null || settings.shadowColor().length() > 0.00001f) {
            component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, settings.shadowColor()));
        } else {
            component.getStyle().setShadow(null);
        }

        Button minimizeButton = component.getMinimizeButton();
        minimizeButton.getStyle().getBackground().setColor(settings.allowColor());
        minimizeButton.getHoveredStyle().getBackground().setColor(settings.allowColor().add(ColorUtil.oppositeBlackOrWhite(settings.allowColor())).div(2));
        minimizeButton.getStyle().setBorder(null);
        minimizeButton.getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.allowColor()));

        component.getCloseButton().getStyle().getBackground().setColor(settings.denyColor());
        component.getCloseButton().getHoveredStyle().getBackground()
            .setColor(settings.denyColor().add(ColorUtil.oppositeBlackOrWhite(settings.denyColor())).div(2));
        component.getCloseButton().getStyle().setBorder(null);
        component.getCloseButton().getTextState().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.denyColor()));

        component.getTitleContainer().getStyle().getBackground().setColor(settings.backgroundColor());

        component.getResizeButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getHoveredStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getPressedStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getStyle().setBorder(null);

        CharIcon icon = new CharIcon(FontRegistry.MATERIAL_DESIGN_ICONS, '\uF45D');
        icon.setSize(new Vector2f(20, 20));
        icon.setPosition(new Vector2f(-10, -10));
        icon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
        component.getResizeButton().getStyle().getBackground().setIcon(icon);

        TextState titleTextState = component.getTitleTextState();
        component.getTitle().getStyle().setPadding(3f, 5f);
        titleTextState.setTextColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
        Icon closeIcon = component.getCloseIcon();
        if (closeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) closeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon minimizeIcon = component.getMinimizeIcon();
        if (minimizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) minimizeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon maximizeIcon = component.getMaximizeIcon();
        if (maximizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) maximizeIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getContainer());
        component.getContainer().getStyle().getBackground().setColor(settings.backgroundColor());
        component.getContainer().getStyle().setBorder(new SimpleLineBorder(settings.borderColor(), 1));
        component.getContainer().getStyle().setShadow(null);

        component.getStyle().setBorder(new SimpleLineBorder(settings.borderColor(), 1));
        component.getTitleContainer().getStyle().setBorder(new SimpleLineBorder(settings.borderColor(), 1));
        component.getTitleContainer().getStyle().setShadow(null);

        component.getCloseButton().getStyle().setShadow(null);
        minimizeButton.getStyle().setShadow(null);
        component.getResizeButton().getStyle().setShadow(null);

    }
}

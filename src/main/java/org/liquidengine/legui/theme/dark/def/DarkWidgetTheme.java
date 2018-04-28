package org.liquidengine.legui.theme.dark.def;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark Widget Theme for all scrollable widgets. Used to make widget dark.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class DarkWidgetTheme<T extends Widget> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        component.getMinimizeButton().getStyle().getBackground().setColor(ColorConstants.lightBlack());
        component.getMinimizeButton().getHoveredStyle().getBackground().setColor(ColorConstants.lightBlack().mul(2f, 2f, 2f, 1f));
        component.getMinimizeButton().getStyle().setBorder(null);
        component.getMinimizeButton().getTextState().setTextColor(ColorConstants.white());
        component.getCloseButton().getStyle().getBackground().setColor(ColorConstants.lightBlack());
        component.getCloseButton().getHoveredStyle().getBackground().setColor(ColorConstants.lightBlack().mul(2f, 2f, 2f, 1f));
        component.getCloseButton().getStyle().setBorder(null);
        component.getCloseButton().getTextState().setTextColor(ColorConstants.white());
        component.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightBlack());
        component.getResizeButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getHoveredStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getPressedStyle().getBackground().setColor(ColorConstants.transparent());
        component.getResizeButton().getStyle().setBorder(null);

        CharIcon icon = new CharIcon(FontRegistry.MATERIAL_DESIGN_ICONS, '\uF45D');
        icon.setSize(new Vector2f(20, 20));
        icon.setPosition(new Vector2f(-10, -10));
        icon.setColor(ColorConstants.white());
        component.getResizeButton().getStyle().getBackground().setIcon(icon);

        TextState titleTextState = component.getTitleTextState();
        titleTextState.setPadding(7, 3, 5, 2);
        titleTextState.setTextColor(ColorConstants.white());
        Icon closeIcon = component.getCloseIcon();
        if (closeIcon != null && closeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) closeIcon;
            bgIcon.setColor(ColorConstants.white());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon minimizeIcon = component.getMinimizeIcon();
        if (minimizeIcon != null && minimizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) minimizeIcon;
            bgIcon.setColor(ColorConstants.white());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon maximizeIcon = component.getMaximizeIcon();
        if (maximizeIcon != null && maximizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) maximizeIcon;
            bgIcon.setColor(ColorConstants.white());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getContainer());
    }
}

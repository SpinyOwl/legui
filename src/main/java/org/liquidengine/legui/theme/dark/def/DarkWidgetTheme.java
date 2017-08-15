package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.ThemeManager;

/**
 * Dark Widget Theme for all scrollable widgets. Used to make widget dark.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class DarkWidgetTheme<T extends Widget> extends DarkControllerTheme<T> {

    private ThemeManager darkThemeManager;

    /**
     * Used to create theme.
     *
     * @param darkThemeManager theme manager.
     */
    public DarkWidgetTheme(ThemeManager darkThemeManager) {
        this.darkThemeManager = darkThemeManager;
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        component.getMinimizeButton().setBackgroundColor(ColorConstants.lightBlack());
        component.getMinimizeButton().getTextState().setTextColor(ColorConstants.white());
        component.getMinimizeButton().setBorder(null);
        component.getCloseButton().setBackgroundColor(ColorConstants.lightBlack());
        component.getCloseButton().getTextState().setTextColor(ColorConstants.white());
        component.getCloseButton().setBorder(null);
        component.getTitleContainer().setBackgroundColor(ColorConstants.lightBlack());
        component.setTitleBackgroundColor(ColorConstants.lightBlack());
        component.getTitleTextState().setTextColor(ColorConstants.white());
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
        AbstractTheme componentTheme = darkThemeManager.getComponentTheme(component.getContainer().getClass());
        componentTheme.applyAll(component.getContainer());
    }
}

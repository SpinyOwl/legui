package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.theme.Themes;

/**
 * White Widget Theme for all scrollable widgets. Used to make widget white.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class WhiteWidgetTheme<T extends Widget> extends WhiteComponentTheme<T> {

    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        component.getMinimizeButton().getStyle().getBackground().setColor(ColorConstants.lightGray());
        component.getMinimizeButton().getTextState().setTextColor(ColorConstants.black());
        component.getMinimizeButton().getStyle().setBorder(null);
        component.getCloseButton().getStyle().getBackground().setColor(ColorConstants.lightGray());
        component.getCloseButton().getTextState().setTextColor(ColorConstants.black());
        component.getCloseButton().getStyle().setBorder(null);
        component.getTitleContainer().getStyle().getBackground().setColor(ColorConstants.lightGray());
        TextState titleTextState = component.getTitleTextState();
        titleTextState.setPadding(7, 3, 5, 2);
        titleTextState.setTextColor(ColorConstants.black());
        Icon closeIcon = component.getCloseIcon();
        if (closeIcon != null && closeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) closeIcon;
            bgIcon.setColor(ColorConstants.black());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon minimizeIcon = component.getMinimizeIcon();
        if (minimizeIcon != null && minimizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) minimizeIcon;
            bgIcon.setColor(ColorConstants.black());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Icon maximizeIcon = component.getMaximizeIcon();
        if (maximizeIcon != null && maximizeIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) maximizeIcon;
            bgIcon.setColor(ColorConstants.black());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getContainer());
    }
}

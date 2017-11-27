package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.theme.Themes;

/**
 * White SelectBox Theme for all select boxes. Used to make select box white.
 *
 * @param <T> {@link SelectBox} subclasses.
 */
public class WhiteSelectBoxTheme<T extends SelectBox> extends WhiteComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getExpandButton().getStyle().setBorder(null);
        component.getExpandButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getSelectionButton().getStyle().setBorder(null);
        component.getSelectionButton().getStyle().getBackground().setColor(ColorConstants.transparent());
        component.getSelectionButton().getTextState().setTextColor(ColorConstants.black());
        Icon collapseIcon = component.getCollapseIcon();
        if (collapseIcon != null && collapseIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) collapseIcon;
            bgIcon.setColor(ColorConstants.black());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }
        Icon expandIcon = component.getExpandIcon();
        if (expandIcon != null && expandIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) expandIcon;
            bgIcon.setColor(ColorConstants.black());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
    }

    @Override
    public void applyAll(T component) {
        super.applyAll(component);

        Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
    }
}

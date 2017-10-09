package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox} subclasses.
 */
public class DarkSelectBoxTheme<T extends SelectBox> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        component.getExpandButton().setBorder(null);
        component.getExpandButton().setBackgroundColor(ColorConstants.transparent());
        component.getSelectionButton().setBorder(null);
        component.getSelectionButton().setBackgroundColor(ColorConstants.transparent());
        component.getSelectionButton().getTextState().setTextColor(ColorConstants.white());
        Icon collapseIcon = component.getCollapseIcon();
        if (collapseIcon != null && collapseIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) collapseIcon;
            bgIcon.setColor(ColorConstants.white());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }
        Icon expandIcon = component.getExpandIcon();
        if (expandIcon != null && expandIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) expandIcon;
            bgIcon.setColor(ColorConstants.white());
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }

        Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
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

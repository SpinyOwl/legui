package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.color.ColorUtil;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox} subclasses.
 */
public class FlatSelectBoxTheme<T extends SelectBox> extends FlatComponentTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatSelectBoxTheme() {
    }

    public FlatSelectBoxTheme(FlatColoredThemeSettings settings) {
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

        Button expandButton = component.getExpandButton();
        expandButton.getStyle().setShadow(null);
        expandButton.getStyle().getBackground().setColor(ColorConstants.transparent());

        Button selectionButton = component.getSelectionButton();
        selectionButton.getStyle().setShadow(null);
        selectionButton.getStyle().getBackground().setColor(ColorConstants.transparent());
        selectionButton.getStyle().setTextColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));

        Icon collapseIcon = component.getCollapseIcon();
        if (collapseIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) collapseIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
            bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
            bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
        }
        Icon expandIcon = component.getExpandIcon();
        if (expandIcon instanceof CharIcon) {
            CharIcon bgIcon = (CharIcon) expandIcon;
            bgIcon.setColor(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor()));
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
        apply(component);

        Themes.getDefaultTheme().applyAll(component.getSelectionListPanel());
    }
}

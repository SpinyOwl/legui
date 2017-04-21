package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.ThemeManager;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox} subclasses.
 */
public class DarkSelectBoxTheme<T extends SelectBox> extends DarkControllerTheme<T> {
    private ThemeManager themeManager;

    public DarkSelectBoxTheme(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        component.getExpandButton().setBorder(null);
        component.getExpandButton().setBackgroundColor(ColorConstants.transparent());
        component.getExpandButton().getTextState().setTextColor(ColorConstants.white());
        component.getSelectionButton().setBorder(null);
        component.getSelectionButton().setBackgroundColor(ColorConstants.transparent());
        component.getSelectionButton().getTextState().setTextColor(ColorConstants.white());
        AbstractTheme<SelectBox.SelectBoxScrollablePanel> componentTheme = (AbstractTheme<SelectBox.SelectBoxScrollablePanel>) themeManager.getComponentTheme(component.getSelectionListPanel().getClass());
        componentTheme.applyAll(component.getSelectionListPanel());
    }

    /**
     * Used to apply theme for component and for all children of this component.
     * Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        AbstractTheme<SelectBox.SelectBoxScrollablePanel> componentTheme = (AbstractTheme<SelectBox.SelectBoxScrollablePanel>) themeManager.getComponentTheme(component.getSelectionListPanel().getClass());
        componentTheme.applyAll(component.getSelectionListPanel());
    }
}

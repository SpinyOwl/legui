package org.liquidengine.legui.theme.dark.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.theme.Themes;

/**
 * Dark ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel dark.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class DarkScrollablePanelTheme<T extends ScrollablePanel> extends DarkComponentTheme<T> {

    /**
     * Used to apply theme only for component and not apply for child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void apply(T component) {
        super.apply(component);
        Container viewport = component.getViewport();
        Themes.getDefaultTheme().apply(viewport);
        Themes.getDefaultTheme().applyAll(component.getVerticalScrollBar());
        Themes.getDefaultTheme().applyAll(component.getHorizontalScrollBar());
        viewport.setBorder(null);
        viewport.setBackgroundColor(ColorConstants.transparent());
    }

    /**
     * Used to apply theme for component and for all children of this component. Should be reimplemented for components that contains other child components.
     *
     * @param component component to apply theme.
     */
    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        Container viewport = component.getViewport();
        Themes.getDefaultTheme().applyAll(viewport);
        viewport.setBorder(null);
        viewport.setBackgroundColor(ColorConstants.transparent());
    }
}

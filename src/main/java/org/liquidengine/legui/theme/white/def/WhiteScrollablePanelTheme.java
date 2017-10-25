package org.liquidengine.legui.theme.white.def;

import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.theme.Themes;

/**
 * White ScrollablePanel Theme for all scrollable panels. Used to make scrollable panel white.
 *
 * @param <T> {@link ScrollablePanel} subclasses.
 */
public class WhiteScrollablePanelTheme<T extends ScrollablePanel> extends WhiteComponentTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        Component viewport = component.getViewport();
        Themes.getDefaultTheme().apply(viewport);
        Themes.getDefaultTheme().apply(component.getVerticalScrollBar());
        Themes.getDefaultTheme().apply(component.getHorizontalScrollBar());
        viewport.setBorder(null);
        viewport.setBackgroundColor(ColorConstants.transparent());
    }

    @Override
    public void applyAll(T component) {
        super.applyAll(component);
        Component viewport = component.getViewport();
        Themes.getDefaultTheme().applyAll(viewport);
        viewport.setBorder(null);
        viewport.setBackgroundColor(ColorConstants.transparent());
    }
}

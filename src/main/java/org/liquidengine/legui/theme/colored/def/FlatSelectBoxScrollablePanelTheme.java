package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark SelectBoxScrollablePanel Theme.
 *
 * @param <T> {@link SelectBox.SelectBoxScrollablePanel} subclasses.
 */
public class FlatSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends FlatScrollablePanelTheme<T> {

    /**
     * Default constructor. Settings should be specified before using this theme.
     */
    public FlatSelectBoxScrollablePanelTheme() {
    }

    public FlatSelectBoxScrollablePanelTheme(FlatColoredThemeSettings settings) {
        super(settings);
    }

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().getBackground().setColor(settings.backgroundColor());
    }
}

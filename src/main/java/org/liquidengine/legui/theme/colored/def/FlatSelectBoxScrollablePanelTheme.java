package org.liquidengine.legui.theme.colored.def;

import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.shadow.Shadow;
import org.liquidengine.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark SelectBoxScrollablePanel Theme.
 *
 * @param <T> {@link SelectBox.SelectBoxScrollablePanel} subclasses.
 */
public class FlatSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends FlatScrollablePanelTheme<T> {

    private FlatColoredThemeSettings settings;

    public FlatSelectBoxScrollablePanelTheme(FlatColoredThemeSettings settings) {
        super(settings);
        this.settings = settings;
    }

    @Override
    public void apply(T component) {
        super.apply(component);

        if (settings.shadowColor()== null || settings.shadowColor().length() > 0.00001f) {
            component.getStyle().setShadow(new Shadow(-4, 4, 17, -7, settings.shadowColor()));
        } else {
            component.getStyle().setShadow(null);
        }

        component.getStyle().getBackground().setColor(ColorConstants.red());
    }
}

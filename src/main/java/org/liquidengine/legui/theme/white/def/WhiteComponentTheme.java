package org.liquidengine.legui.theme.white.def;

import java.util.List;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.Themes;

/**
 * White Component Theme for all components. Used to make component white.
 *
 * @param <T> {@link Component} subclasses.
 */
public class WhiteComponentTheme<T extends Component> extends AbstractTheme<T> {

    @Override
    public void apply(T component) {
        super.apply(component);
        component.getStyle().setBorder(new SimpleLineBorder(ColorConstants.darkGray(), .7f));
        component.getStyle().setCornerRadius(2);
        component.getStyle().getBackground().setColor(ColorConstants.white());
        Tooltip tooltip = component.getTooltip();
        if (tooltip != null) {
            Themes.getDefaultTheme().applyAll(tooltip);
        }
        List<? extends Component> childs = component.getChilds();
        for (Component child : childs) {
            Themes.getDefaultTheme().applyAll(child);
        }
    }
}

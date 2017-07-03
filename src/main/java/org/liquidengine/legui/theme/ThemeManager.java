package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;

/**
 * Created by Aliaksandr_Shcherbin on 3/9/2017.
 */
public abstract class ThemeManager {
    public abstract <T extends Component> AbstractTheme<T> getComponentTheme(Class<T> clazz);

    public abstract <T extends Component> void setComponentTheme(Class<T> clazz, AbstractTheme<T> theme);

    public <T extends Component> void applyAll(T component) {
        getComponentTheme((Class<T>) component.getClass()).applyAll(component);
    }

    public <T extends Component> void apply(T component) {
        getComponentTheme((Class<T>) component.getClass()).apply(component);
    }
}

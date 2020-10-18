package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;

/**
 * Created by ShchAlexander on 2/6/2017.
 */
public abstract class Theme {

    private final ThemeManager themeManager;

    protected Theme(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public <T extends Component> void apply(T component) {
        themeManager.getComponentTheme((Class<T>) component.getClass()).apply(component);
    }

    public <T extends Component> void applyAll(T component) {
        themeManager.getComponentTheme((Class<T>) component.getClass()).applyAll(component);
    }

    public void applyAll(Frame frame) {
        frame.getAllLayers().forEach(this::applyAll);
    }
}

package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public abstract class Theme {
    private static Theme DEFAULT_THEME = new DefaultTheme();
    private ThemeManager themeManager;

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    protected Theme(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    public static Theme getDefaultTheme() {
        return DEFAULT_THEME;
    }

    public static void setDefaultTheme(Theme defaultTheme) {
        if (defaultTheme != null) {
            DEFAULT_THEME = defaultTheme;
        }
    }

    public <T extends Component> void apply(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.apply(component);
    }

    public <T extends Component> void applyAll(T component) {
        themeManager.applyAll(component);
    }

    public void applyAll(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAll(allLayer.getContainer());
        }
    }

}

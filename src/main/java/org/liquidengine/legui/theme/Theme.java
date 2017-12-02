package org.liquidengine.legui.theme;

import java.util.List;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;

/**
 * Created by ShchAlexander on 2/6/2017.
 */
public abstract class Theme {

    private ThemeManager themeManager;

    protected Theme(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public <T extends Component> void apply(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.apply(component);
    }

    public <T extends Component> void applyAll(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.applyAll(component);
    }

    public void applyAll(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAll(allLayer.getContainer());
        }
    }
}

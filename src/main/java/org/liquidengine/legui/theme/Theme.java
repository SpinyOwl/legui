package org.liquidengine.legui.theme;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Layer;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
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


    public <T extends Component> void applyWithParent(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.applyWithParent(component);
    }

    public <T extends Component> void applyAllWithParent(T component) {
        AbstractTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.applyAllWithParent(component);
    }

    public void applyAll(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAll(allLayer.getContainer());
        }
    }

    public void applyAllWithParent(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAllWithParent(allLayer.getContainer());
        }
    }

}

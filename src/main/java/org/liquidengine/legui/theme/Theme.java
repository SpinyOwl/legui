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
        IComponentTheme<T> componentTheme = themeManager.getComponentTheme((Class<T>) component.getClass());
        componentTheme.apply(component);
    }

    public <T extends Component> void applyAll(T component) {
        themeManager.applyAll(component);
    }
    public <T extends Component> void applyAll(Frame frame) {
        List<Layer> allLayers = frame.getAllLayers();
        for (Layer allLayer : allLayers) {
            applyAll(allLayer.getContainer());
        }
    }

    //    public abstract Vector4f backgroundColor();
//
//    public abstract Border border();
//
//    public abstract String font();
//
//    public abstract Vector4f fontColor();
//
//    public abstract Vector4f highlightColor();
//
//    public abstract Vector4f textPadding();
//
//    public abstract float fontSize();
//
//    public abstract HorizontalAlign horizontalAlign();
//
//    public abstract VerticalAlign verticalAlign();
//
//    public abstract float cornerRadius();
//
//    public abstract Vector4f scrollBarArrowColor();
//
//    public abstract Vector4f scrollBarColor();
//
//    public abstract float scrollBarArrowSize();
//
//    public abstract boolean scrollBarArrowsEnabled();
//
//    public abstract Vector4f getFocusedStrokeColorLight();

}

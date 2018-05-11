package org.liquidengine.legui.theme.colored;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.SelectBox.SelectBoxElement;
import org.liquidengine.legui.component.SelectBox.SelectBoxScrollablePanel;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.theme.DefaultThemeManager;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.ThemeManager;
import org.liquidengine.legui.theme.colored.def.FlatColoredComponentTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredLabelTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredLayerContainerTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredProgressBarTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredRadioButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredScrollBarTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredScrollablePanelTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredSelectBoxElementTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredSelectBoxScrollablePanelTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredSelectBoxTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredSliderTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredTextAreaTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredTextInputTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredToggleButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredTooltipTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredWidgetTheme;
import org.liquidengine.legui.theme.colored.def.FlatColoredButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatFlatColoredCheckBoxTheme;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class FlatColoredTheme extends Theme {

    /**
     * Used to create theme instance.
     */
    public FlatColoredTheme(Vector4f backgroundColor1, Vector4f backgroundColor2, Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor) {
        super(createThemeManager(backgroundColor1, backgroundColor2, strokeColor, allowColor, denyColor));
    }

    /**
     * Used to initialize theme manager.
     *
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager(Vector4f bg1, Vector4f bg2, Vector4f sColor, Vector4f aColor, Vector4f dColor) {
        ThemeManager m = new DefaultThemeManager();
        //@formatter:off
        m.setComponentTheme(Button.class,                   new FlatColoredButtonTheme<>               (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(CheckBox.class,                 new FlatFlatColoredCheckBoxTheme<>             (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Component.class,                new FlatColoredComponentTheme<>                (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Label.class,                    new FlatColoredLabelTheme<>                    (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(LayerContainer.class,           new FlatColoredLayerContainerTheme<>           (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ProgressBar.class,              new FlatColoredProgressBarTheme<>              (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ScrollablePanel.class,          new FlatColoredScrollablePanelTheme<>          (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(RadioButton.class,              new FlatColoredRadioButtonTheme<>              (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ScrollBar.class,                new FlatColoredScrollBarTheme<>                (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBox.class,                new FlatColoredSelectBoxTheme<>                (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBoxScrollablePanel.class, new FlatColoredSelectBoxScrollablePanelTheme<> (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBoxElement.class,         new FlatColoredSelectBoxElementTheme<>         (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Slider.class,                   new FlatColoredSliderTheme<>                   (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(TextArea.class,                 new FlatColoredTextAreaTheme<>                 (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(TextInput.class,                new FlatColoredTextInputTheme<>                (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ToggleButton.class,             new FlatColoredToggleButtonTheme<>             (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Tooltip.class,                  new FlatColoredTooltipTheme<>                  (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Widget.class,                   new FlatColoredWidgetTheme<>                   (c(bg1), c(bg2), c(sColor), c(aColor), c(dColor)));
        //@formatter:on
        return m;
    }

    private static Vector4f c(Vector4f s) {
        return new Vector4f(s);
    }

}

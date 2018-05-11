package org.liquidengine.legui.theme.colored;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.component.Panel;
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
import org.liquidengine.legui.theme.colored.def.FlatComponentTheme;
import org.liquidengine.legui.theme.colored.def.FlatLabelTheme;
import org.liquidengine.legui.theme.colored.def.FlatLayerContainerTheme;
import org.liquidengine.legui.theme.colored.def.FlatPanelTheme;
import org.liquidengine.legui.theme.colored.def.FlatProgressBarTheme;
import org.liquidengine.legui.theme.colored.def.FlatRadioButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatScrollBarTheme;
import org.liquidengine.legui.theme.colored.def.FlatScrollablePanelTheme;
import org.liquidengine.legui.theme.colored.def.FlatSelectBoxElementTheme;
import org.liquidengine.legui.theme.colored.def.FlatSelectBoxScrollablePanelTheme;
import org.liquidengine.legui.theme.colored.def.FlatSelectBoxTheme;
import org.liquidengine.legui.theme.colored.def.FlatSliderTheme;
import org.liquidengine.legui.theme.colored.def.FlatTextAreaTheme;
import org.liquidengine.legui.theme.colored.def.FlatTextInputTheme;
import org.liquidengine.legui.theme.colored.def.FlatToggleButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatTooltipTheme;
import org.liquidengine.legui.theme.colored.def.FlatWidgetTheme;
import org.liquidengine.legui.theme.colored.def.FlatButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatCheckBoxTheme;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class FlatColoredTheme extends Theme {

    /**
     * Used to create theme instance.
     */
    public FlatColoredTheme(
        Vector4f defBgColor, Vector4f borderColor,
        Vector4f strokeColor, Vector4f allowColor, Vector4f denyColor
    ) {
        super(createThemeManager(defBgColor, borderColor, strokeColor, allowColor, denyColor));
    }

    /**
     * Used to initialize theme manager.
     *
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager(Vector4f bg1, Vector4f borderC, Vector4f sColor, Vector4f aColor, Vector4f dColor) {
        ThemeManager m = new DefaultThemeManager();
        //@formatter:off
        m.setComponentTheme(Button.class,
                            new FlatButtonTheme<>                   (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Panel.class,
                            new FlatPanelTheme<>                    (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(CheckBox.class,
                            new FlatCheckBoxTheme<>                 (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Component.class,
                            new FlatComponentTheme<>                (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Label.class,
                            new FlatLabelTheme<>                    (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(LayerContainer.class,
                            new FlatLayerContainerTheme<>           (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ProgressBar.class,
                            new FlatProgressBarTheme<>              (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ScrollablePanel.class,
                            new FlatScrollablePanelTheme<>          (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(RadioButton.class,
                            new FlatRadioButtonTheme<>              (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ScrollBar.class,
                            new FlatScrollBarTheme<>                (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBox.class,
                            new FlatSelectBoxTheme<>                (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBoxScrollablePanel.class,
                            new FlatSelectBoxScrollablePanelTheme<> (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(SelectBoxElement.class,
                            new FlatSelectBoxElementTheme<>         (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Slider.class,
                            new FlatSliderTheme<>                   (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(TextArea.class,
                            new FlatTextAreaTheme<>                 (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(TextInput.class,
                            new FlatTextInputTheme<>                (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(ToggleButton.class,
                            new FlatToggleButtonTheme<>             (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Tooltip.class,
                            new FlatTooltipTheme<>                  (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        m.setComponentTheme(Widget.class,
                            new FlatWidgetTheme<>                   (c(bg1), c(borderC), c(sColor), c(aColor), c(dColor)));
        //@formatter:on
        return m;
    }

    private static Vector4f c(Vector4f s) {
        return new Vector4f(s);
    }

}

package org.liquidengine.legui.theme.dark;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Container;
import org.liquidengine.legui.component.Controller;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.theme.DefaultThemeManager;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.ThemeManager;
import org.liquidengine.legui.theme.dark.def.DarkButtonTheme;
import org.liquidengine.legui.theme.dark.def.DarkCheckBoxTheme;
import org.liquidengine.legui.theme.dark.def.DarkComponentTheme;
import org.liquidengine.legui.theme.dark.def.DarkContainerTheme;
import org.liquidengine.legui.theme.dark.def.DarkControllerTheme;
import org.liquidengine.legui.theme.dark.def.DarkLabelTheme;
import org.liquidengine.legui.theme.dark.def.DarkLayerContainerTheme;
import org.liquidengine.legui.theme.dark.def.DarkProgressBarTheme;
import org.liquidengine.legui.theme.dark.def.DarkRadioButtonTheme;
import org.liquidengine.legui.theme.dark.def.DarkScrollBarTheme;
import org.liquidengine.legui.theme.dark.def.DarkScrollablePanelTheme;
import org.liquidengine.legui.theme.dark.def.DarkSelectBoxElementTheme;
import org.liquidengine.legui.theme.dark.def.DarkSelectBoxScrollablePanelTheme;
import org.liquidengine.legui.theme.dark.def.DarkSelectBoxTheme;
import org.liquidengine.legui.theme.dark.def.DarkSliderTheme;
import org.liquidengine.legui.theme.dark.def.DarkTextAreaTheme;
import org.liquidengine.legui.theme.dark.def.DarkTextInputTheme;
import org.liquidengine.legui.theme.dark.def.DarkToggleButtonTheme;
import org.liquidengine.legui.theme.dark.def.DarkTooltipTheme;
import org.liquidengine.legui.theme.dark.def.DarkWidgetTheme;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class DarkTheme extends Theme {

    /**
     * Used to create theme instance.
     */
    public DarkTheme() {
        super(createThemeManager());
    }

    /**
     * Used to initialize theme manager.
     *
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Button.class, new DarkButtonTheme<>());
        manager.setComponentTheme(CheckBox.class, new DarkCheckBoxTheme<>());
        manager.setComponentTheme(Component.class, new DarkComponentTheme<>());
        manager.setComponentTheme(Container.class, new DarkContainerTheme<>());
        manager.setComponentTheme(Controller.class, new DarkControllerTheme<>());
        manager.setComponentTheme(Label.class, new DarkLabelTheme<>());
        manager.setComponentTheme(LayerContainer.class, new DarkLayerContainerTheme<>());
        manager.setComponentTheme(ProgressBar.class, new DarkProgressBarTheme<>());
        manager.setComponentTheme(ScrollablePanel.class, new DarkScrollablePanelTheme<>());
        manager.setComponentTheme(RadioButton.class, new DarkRadioButtonTheme<>());
        manager.setComponentTheme(ScrollBar.class, new DarkScrollBarTheme<>());
        manager.setComponentTheme(SelectBox.class, new DarkSelectBoxTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new DarkSelectBoxScrollablePanelTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxElement.class, new DarkSelectBoxElementTheme<>());
        manager.setComponentTheme(Slider.class, new DarkSliderTheme<>());
        manager.setComponentTheme(TextArea.class, new DarkTextAreaTheme<>());
        manager.setComponentTheme(TextInput.class, new DarkTextInputTheme<>());
        manager.setComponentTheme(ToggleButton.class, new DarkToggleButtonTheme<>());
        manager.setComponentTheme(Tooltip.class, new DarkTooltipTheme<>());
        manager.setComponentTheme(Widget.class, new DarkWidgetTheme<>());
        return manager;
    }

}

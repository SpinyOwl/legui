package org.liquidengine.legui.theme.dark;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.theme.DefaultThemeManager;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.ThemeManager;
import org.liquidengine.legui.theme.dark.def.*;

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
        manager.setComponentTheme(SelectBox.class, new DarkSelectBoxTheme<>(manager));
        manager.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new DarkSelectBoxScrollablePanelTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxElement.class, new DarkSelectBoxElementTheme<>());
        manager.setComponentTheme(Slider.class, new DarkSliderTheme<>());
        manager.setComponentTheme(TextArea.class, new DarkTextAreaTheme<>());
        manager.setComponentTheme(TextInput.class, new DarkTextInputTheme<>());
        manager.setComponentTheme(ToggleButton.class, new DarkToggleButtonTheme<>());
        manager.setComponentTheme(Tooltip.class, new DarkTooltipTheme<>());
        manager.setComponentTheme(Widget.class, new DarkWidgetTheme<>(manager));
        return manager;
    }

}

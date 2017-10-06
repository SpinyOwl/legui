package org.liquidengine.legui.theme.white;

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
import org.liquidengine.legui.theme.white.def.WhiteButtonTheme;
import org.liquidengine.legui.theme.white.def.WhiteCheckBoxTheme;
import org.liquidengine.legui.theme.white.def.WhiteComponentTheme;
import org.liquidengine.legui.theme.white.def.WhiteContainerTheme;
import org.liquidengine.legui.theme.white.def.WhiteControllerTheme;
import org.liquidengine.legui.theme.white.def.WhiteLabelTheme;
import org.liquidengine.legui.theme.white.def.WhiteLayerContainerTheme;
import org.liquidengine.legui.theme.white.def.WhiteProgressBarTheme;
import org.liquidengine.legui.theme.white.def.WhiteRadioButtonTheme;
import org.liquidengine.legui.theme.white.def.WhiteScrollBarTheme;
import org.liquidengine.legui.theme.white.def.WhiteScrollablePanelTheme;
import org.liquidengine.legui.theme.white.def.WhiteSelectBoxElementTheme;
import org.liquidengine.legui.theme.white.def.WhiteSelectBoxScrollablePanelTheme;
import org.liquidengine.legui.theme.white.def.WhiteSelectBoxTheme;
import org.liquidengine.legui.theme.white.def.WhiteSliderTheme;
import org.liquidengine.legui.theme.white.def.WhiteTextAreaTheme;
import org.liquidengine.legui.theme.white.def.WhiteTextInputTheme;
import org.liquidengine.legui.theme.white.def.WhiteToggleButtonTheme;
import org.liquidengine.legui.theme.white.def.WhiteTooltipTheme;
import org.liquidengine.legui.theme.white.def.WhiteWidgetTheme;

/**
 * Default white theme which used by ThemeManager as default theme.
 */
public class WhiteTheme extends Theme {

    public WhiteTheme() {
        super(createThemeManager());
    }

    private static ThemeManager createThemeManager() {
        ThemeManager manager = new DefaultThemeManager();
        manager.setComponentTheme(Button.class, new WhiteButtonTheme<>());
        manager.setComponentTheme(CheckBox.class, new WhiteCheckBoxTheme<>());
        manager.setComponentTheme(Component.class, new WhiteComponentTheme<>());
        manager.setComponentTheme(Container.class, new WhiteContainerTheme<>());
        manager.setComponentTheme(Controller.class, new WhiteControllerTheme<>());
//        manager.setComponentTheme(Dialog.class, new DefaultDialogTheme<>());
//        manager.setComponentTheme(ImageView.class, new DefaultImageViewTheme<>());
        manager.setComponentTheme(Label.class, new WhiteLabelTheme<>());
        manager.setComponentTheme(LayerContainer.class, new WhiteLayerContainerTheme<>());
//        manager.setComponentTheme(Panel.class, new DefaultPanelTheme<>());
        manager.setComponentTheme(ProgressBar.class, new WhiteProgressBarTheme<>());
        manager.setComponentTheme(ScrollablePanel.class, new WhiteScrollablePanelTheme<>());
        manager.setComponentTheme(RadioButton.class, new WhiteRadioButtonTheme<>());
        manager.setComponentTheme(ScrollBar.class, new WhiteScrollBarTheme<>());
        manager.setComponentTheme(SelectBox.class, new WhiteSelectBoxTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxScrollablePanel.class, new WhiteSelectBoxScrollablePanelTheme<>());
        manager.setComponentTheme(SelectBox.SelectBoxElement.class, new WhiteSelectBoxElementTheme<>());
        manager.setComponentTheme(Slider.class, new WhiteSliderTheme<>());
        manager.setComponentTheme(TextArea.class, new WhiteTextAreaTheme<>());
        manager.setComponentTheme(TextInput.class, new WhiteTextInputTheme<>());
        manager.setComponentTheme(ToggleButton.class, new WhiteToggleButtonTheme<>());
        manager.setComponentTheme(Tooltip.class, new WhiteTooltipTheme<>());
        manager.setComponentTheme(Widget.class, new WhiteWidgetTheme<>());
        return manager;
    }

}

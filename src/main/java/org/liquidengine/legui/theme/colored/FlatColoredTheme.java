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
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.theme.AbstractTheme;
import org.liquidengine.legui.theme.DefaultThemeManager;
import org.liquidengine.legui.theme.Theme;
import org.liquidengine.legui.theme.ThemeManager;
import org.liquidengine.legui.theme.colored.def.FlatButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatCheckBoxTheme;
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
import org.liquidengine.legui.theme.colored.def.FlatTextAreaFieldTheme;
import org.liquidengine.legui.theme.colored.def.FlatTextAreaTheme;
import org.liquidengine.legui.theme.colored.def.FlatTextInputTheme;
import org.liquidengine.legui.theme.colored.def.FlatToggleButtonTheme;
import org.liquidengine.legui.theme.colored.def.FlatTooltipTheme;
import org.liquidengine.legui.theme.colored.def.FlatWidgetTheme;

/**
 * Dark Theme. Used to change theme of components to dark.
 */
public class FlatColoredTheme extends Theme {

    /**
     * Used to create theme instance.
     *
     * @param backgroundColor background color.
     * @param borderColor border color.
     * @param strokeColor stroke color.
     * @param allowColor allow color.
     * @param denyColor deny color.
     * @param shadowColor shadow color.
     */
    public FlatColoredTheme(
        Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor,
        Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
                           ) {
        super(createThemeManager(new FlatColoredThemeSettings(backgroundColor, borderColor, strokeColor, allowColor, denyColor, shadowColor)));
    }

    /**
     * Used to create theme instance.
     *
     * @param backgroundColor background color.
     * @param borderColor border color.
     * @param sliderColor slider color.
     * @param strokeColor stroke color.
     * @param allowColor allow color.
     * @param denyColor deny color.
     * @param shadowColor shadow color.
     */
    public FlatColoredTheme(
        Vector4f backgroundColor, Vector4f borderColor, Vector4f sliderColor, Vector4f strokeColor,
        Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
                           ) {
        super(createThemeManager(new FlatColoredThemeSettings(backgroundColor, borderColor,sliderColor, strokeColor, allowColor, denyColor, shadowColor)));
    }

    /**
     * Used to initialize theme manager.
     *
     * @param settings settings to use by components theme.
     * @return initialized theme manager.
     */
    private static ThemeManager createThemeManager(FlatColoredThemeSettings settings) {
        ThemeManager m = new DefaultThemeManager() {
            @Override
            public <T extends Component> void setComponentTheme(Class<T> clazz, AbstractTheme<T> theme) {
                if (theme instanceof FlatComponentTheme) {
                    FlatComponentTheme flatComponentTheme = (FlatComponentTheme) theme;
                    if (flatComponentTheme.getSettings() == null) {
                        flatComponentTheme.setSettings(settings);
                    }
                }
                super.setComponentTheme(clazz, theme);
            }
        };
        //@formatter:off
        m.setComponentTheme(Button.class, new FlatButtonTheme<>());
        m.setComponentTheme(Panel.class, new FlatPanelTheme<>());
        m.setComponentTheme(CheckBox.class, new FlatCheckBoxTheme<>());
        m.setComponentTheme(Component.class, new FlatComponentTheme<>());
        m.setComponentTheme(Label.class, new FlatLabelTheme<>());
        m.setComponentTheme(LayerContainer.class, new FlatLayerContainerTheme<>());
        m.setComponentTheme(ProgressBar.class, new FlatProgressBarTheme<>());
        m.setComponentTheme(ScrollablePanel.class, new FlatScrollablePanelTheme<>());
        m.setComponentTheme(RadioButton.class, new FlatRadioButtonTheme<>());
        m.setComponentTheme(ScrollBar.class, new FlatScrollBarTheme<>());
        m.setComponentTheme(SelectBox.class, new FlatSelectBoxTheme<>());
        m.setComponentTheme(SelectBoxScrollablePanel.class, new FlatSelectBoxScrollablePanelTheme<>());
        m.setComponentTheme(SelectBoxElement.class, new FlatSelectBoxElementTheme<>());
        m.setComponentTheme(Slider.class, new FlatSliderTheme<>());
        m.setComponentTheme(TextArea.class, new FlatTextAreaTheme<>());
        m.setComponentTheme(TextAreaField.class, new FlatTextAreaFieldTheme<>());
        m.setComponentTheme(TextInput.class, new FlatTextInputTheme<>());
        m.setComponentTheme(ToggleButton.class, new FlatToggleButtonTheme<>());
        m.setComponentTheme(Tooltip.class, new FlatTooltipTheme<>());
        m.setComponentTheme(Widget.class, new FlatWidgetTheme<>());
        //@formatter:on
        return m;
    }

    /**
     * Flat colored theme settings.
     */
    public static class FlatColoredThemeSettings {

        /**
         * Background color.
         */
        private final Vector4f backgroundColor;
        /**
         * Border color.
         */
        private final Vector4f borderColor;
        /**
         * Slider color.
         */
        private final Vector4f sliderColor;
        /**
         * Stroke color.
         */
        private final Vector4f allowColor;
        /**
         * Allow color.
         */
        private final Vector4f strokeColor;
        /**
         * Deny color.
         */
        private final Vector4f denyColor;
        /**
         * Shadow color.
         */
        private final Vector4f shadowColor;

        /**
         * Used to create theme settings instance.
         *
         * @param backgroundColor background color.
         * @param borderColor border color.
         * @param sliderColor slider color.
         * @param strokeColor stroke color.
         * @param allowColor allow color.
         * @param denyColor deny color.
         * @param shadowColor shadow color.
         */
        public FlatColoredThemeSettings(
            Vector4f backgroundColor, Vector4f borderColor, Vector4f sliderColor, Vector4f strokeColor,
            Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
                                       ) {
            this.backgroundColor = backgroundColor;
            this.borderColor = borderColor;
            this.sliderColor = sliderColor;
            this.allowColor = allowColor;
            this.strokeColor = strokeColor;
            this.denyColor = denyColor;
            this.shadowColor = shadowColor;
        }

        /**
         * Used to create theme settings instance.
         *
         * @param backgroundColor background color.
         * @param borderColor border color (also used as slider color).
         * @param strokeColor stroke color.
         * @param allowColor allow color.
         * @param denyColor deny color.
         * @param shadowColor shadow color.
         */
        public FlatColoredThemeSettings(
            Vector4f backgroundColor, Vector4f borderColor, Vector4f strokeColor,
            Vector4f allowColor, Vector4f denyColor, Vector4f shadowColor
                                       ) {
            this(backgroundColor, borderColor, borderColor, strokeColor, allowColor, denyColor, shadowColor);
        }

        /**
         * Returns background color.
         *
         * @return background color.
         */
        public Vector4f backgroundColor() {
            return backgroundColor == null ? null : new Vector4f(backgroundColor);
        }

        /**
         * Returns border color.
         *
         * @return border color.
         */
        public Vector4f borderColor() {
            return borderColor == null ? null : new Vector4f(borderColor);
        }


        /**
         * Returns slider color.
         *
         * @return slider color.
         */
        public Vector4f sliderColor() {
            return sliderColor == null ? null : new Vector4f(sliderColor);
        }

        /**
         * Returns stroke color.
         *
         * @return stroke color.
         */
        public Vector4f strokeColor() {
            return strokeColor == null ? null : new Vector4f(strokeColor);
        }

        /**
         * Returns allow color.
         *
         * @return allow color.
         */
        public Vector4f allowColor() {
            return allowColor == null ? null : new Vector4f(allowColor);
        }

        /**
         * Returns deny color.
         *
         * @return deny color.
         */
        public Vector4f denyColor() {
            return denyColor == null ? null : new Vector4f(denyColor);
        }

        /**
         * Returns shadow color.
         *
         * @return shadow color.
         */
        public Vector4f shadowColor() {
            return shadowColor == null ? null : new Vector4f(shadowColor);
        }
    }

}

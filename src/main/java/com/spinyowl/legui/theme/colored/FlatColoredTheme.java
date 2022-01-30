package com.spinyowl.legui.theme.colored;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.CheckBox;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.Layer;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.ProgressBar;
import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.ScrollablePanel;
import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.component.SelectBox.SelectBoxElement;
import com.spinyowl.legui.component.SelectBox.SelectBoxScrollablePanel;
import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.component.TextArea;
import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.component.ToggleButton;
import com.spinyowl.legui.component.Tooltip;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.theme.AbstractTheme;
import com.spinyowl.legui.theme.DefaultThemeManager;
import com.spinyowl.legui.theme.Theme;
import com.spinyowl.legui.theme.ThemeManager;
import com.spinyowl.legui.theme.colored.def.FlatButtonTheme;
import com.spinyowl.legui.theme.colored.def.FlatCheckBoxTheme;
import com.spinyowl.legui.theme.colored.def.FlatComponentTheme;
import com.spinyowl.legui.theme.colored.def.FlatLabelTheme;
import com.spinyowl.legui.theme.colored.def.FlatLayerTheme;
import com.spinyowl.legui.theme.colored.def.FlatPanelTheme;
import com.spinyowl.legui.theme.colored.def.FlatProgressBarTheme;
import com.spinyowl.legui.theme.colored.def.FlatRadioButtonTheme;
import com.spinyowl.legui.theme.colored.def.FlatScrollBarTheme;
import com.spinyowl.legui.theme.colored.def.FlatScrollablePanelTheme;
import com.spinyowl.legui.theme.colored.def.FlatSelectBoxElementTheme;
import com.spinyowl.legui.theme.colored.def.FlatSelectBoxScrollablePanelTheme;
import com.spinyowl.legui.theme.colored.def.FlatSelectBoxTheme;
import com.spinyowl.legui.theme.colored.def.FlatSliderTheme;
import com.spinyowl.legui.theme.colored.def.FlatTextAreaFieldTheme;
import com.spinyowl.legui.theme.colored.def.FlatTextAreaTheme;
import com.spinyowl.legui.theme.colored.def.FlatTextInputTheme;
import com.spinyowl.legui.theme.colored.def.FlatToggleButtonTheme;
import com.spinyowl.legui.theme.colored.def.FlatTooltipTheme;
import com.spinyowl.legui.theme.colored.def.FlatWidgetTheme;
import org.joml.Vector4f;

/** Dark Theme. Used to change theme of components to dark. */
public class FlatColoredTheme extends Theme {

  public static final float FONT_SIZE = 12f;

  /**
   * Used to create theme instance.
   *
   * @param backgroundColor background color.
   * @param borderColor border color.
   * @param strokeColor stroke color.
   * @param allowColor allow color.
   * @param denyColor deny color.
   * @param shadowColor shadow color.
   * @param textColor text color.
   */
  public FlatColoredTheme(
      Vector4f backgroundColor,
      Vector4f borderColor,
      Vector4f strokeColor,
      Vector4f allowColor,
      Vector4f denyColor,
      Vector4f shadowColor,
      Vector4f textColor) {
    super(
        createThemeManager(
            new FlatColoredThemeSettings(
                backgroundColor,
                borderColor,
                borderColor,
                strokeColor,
                allowColor,
                denyColor,
                shadowColor,
                textColor,
                FontRegistry.getDefaultFont(),
                FONT_SIZE)));
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
   * @param textColor text color.
   */
  public FlatColoredTheme(
      Vector4f backgroundColor,
      Vector4f borderColor,
      Vector4f sliderColor,
      Vector4f strokeColor,
      Vector4f allowColor,
      Vector4f denyColor,
      Vector4f shadowColor,
      Vector4f textColor) {
    super(
        createThemeManager(
            new FlatColoredThemeSettings(
                backgroundColor,
                borderColor,
                sliderColor,
                strokeColor,
                allowColor,
                denyColor,
                shadowColor,
                textColor,
                FontRegistry.getDefaultFont(),
                FONT_SIZE)));
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
   * @param textColor text color.
   * @param fontSize font size.
   */
  public FlatColoredTheme(
      Vector4f backgroundColor,
      Vector4f borderColor,
      Vector4f sliderColor,
      Vector4f strokeColor,
      Vector4f allowColor,
      Vector4f denyColor,
      Vector4f shadowColor,
      Vector4f textColor,
      String font,
      Float fontSize) {
    super(
        createThemeManager(
            new FlatColoredThemeSettings(
                backgroundColor,
                borderColor,
                sliderColor,
                strokeColor,
                allowColor,
                denyColor,
                shadowColor,
                textColor,
                font,
                fontSize)));
  }

  /**
   * Used to initialize theme manager.
   *
   * @param settings settings to use by components theme.
   * @return initialized theme manager.
   */
  private static ThemeManager createThemeManager(FlatColoredThemeSettings settings) {
    ThemeManager m =
        new DefaultThemeManager() {
          @Override
          public <T extends Component> void setComponentTheme(
              Class<T> clazz, AbstractTheme<T> theme) {
            if (theme instanceof FlatComponentTheme) {
              FlatComponentTheme flatComponentTheme = (FlatComponentTheme) theme;
              if (flatComponentTheme.getSettings() == null) {
                flatComponentTheme.setSettings(settings);
              }
            }
            super.setComponentTheme(clazz, theme);
          }
        };
    // @formatter:off
    m.setComponentTheme(Button.class, new FlatButtonTheme<>());
    m.setComponentTheme(Panel.class, new FlatPanelTheme<>());
    m.setComponentTheme(CheckBox.class, new FlatCheckBoxTheme<>());
    m.setComponentTheme(Component.class, new FlatComponentTheme<>());
    m.setComponentTheme(Label.class, new FlatLabelTheme<>());
    m.setComponentTheme(Layer.class, new FlatLayerTheme<>());
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
    // @formatter:on
    return m;
  }

  /** Flat colored theme settings. */
  public static class FlatColoredThemeSettings {

    /** Background color. */
    private final Vector4f backgroundColor;
    /** Border color. */
    private final Vector4f borderColor;
    /** Slider color. */
    private final Vector4f sliderColor;
    /** Stroke color. */
    private final Vector4f allowColor;
    /** Allow color. */
    private final Vector4f strokeColor;
    /** Deny color. */
    private final Vector4f denyColor;
    /** Shadow color. */
    private final Vector4f shadowColor;
    /** Font color. */
    private final Vector4f textColor;

    private final String font;
    /** Font size */
    private final Float fontSize;

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
     * @param textColor text color.
     * @param fontSize font size.
     */
    public FlatColoredThemeSettings(
        Vector4f backgroundColor,
        Vector4f borderColor,
        Vector4f sliderColor,
        Vector4f strokeColor,
        Vector4f allowColor,
        Vector4f denyColor,
        Vector4f shadowColor,
        Vector4f textColor,
        String font,
        Float fontSize) {
      this.backgroundColor = backgroundColor;
      this.borderColor = borderColor;
      this.sliderColor = sliderColor;
      this.allowColor = allowColor;
      this.strokeColor = strokeColor;
      this.denyColor = denyColor;
      this.shadowColor = shadowColor;
      this.textColor = textColor;
      this.font = font;
      this.fontSize = fontSize;
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
        Vector4f backgroundColor,
        Vector4f borderColor,
        Vector4f strokeColor,
        Vector4f allowColor,
        Vector4f denyColor,
        Vector4f shadowColor) {
      this(
          backgroundColor,
          borderColor,
          borderColor,
          strokeColor,
          allowColor,
          denyColor,
          shadowColor,
          borderColor,
          FontRegistry.getDefaultFont(),
          FONT_SIZE);
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

    public String font() {
      return font;
    }

    public Float fontSize() {
      return fontSize;
    }

    public Vector4f textColor() {
      return textColor == null ? null : new Vector4f(textColor);
    }
  }
}

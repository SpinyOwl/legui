package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Slider Theme for all sliders. Used to make slider dark.
 *
 * @param <T> {@link Slider} subclasses.
 */
public class FlatSliderTheme<T extends Slider> extends FlatBorderlessTransparentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatSliderTheme() {
  }

  public FlatSliderTheme(FlatColoredThemeSettings settings) {
    super(settings);
  }

  /**
   * Used to apply theme only for component and not apply for child components.
   *
   * @param component component to apply theme.
   */
  @Override
  public void apply(T component) {
    super.apply(component);
    component.setSliderColor(settings.sliderColor());
    component.setSliderActiveColor(settings.allowColor());
  }
}

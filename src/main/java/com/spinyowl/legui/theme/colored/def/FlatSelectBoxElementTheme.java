package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark SelectBox Theme for all select boxes. Used to make select box dark.
 *
 * @param <T> {@link SelectBox.SelectBoxElement} subclasses.
 */
public class FlatSelectBoxElementTheme<T extends SelectBox.SelectBoxElement> extends
    FlatBorderlessTransparentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatSelectBoxElementTheme() {
  }

  public FlatSelectBoxElementTheme(FlatColoredThemeSettings settings) {
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
  }
}

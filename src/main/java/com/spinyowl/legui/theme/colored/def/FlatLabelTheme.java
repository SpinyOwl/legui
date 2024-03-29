package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark Label Theme for all labels. Used to make label dark.
 *
 * @param <T> {@link Label} subclasses.
 */
public class FlatLabelTheme<T extends Label> extends FlatBorderlessTransparentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatLabelTheme() {
  }

  public FlatLabelTheme(FlatColoredThemeSettings settings) {
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

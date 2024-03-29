package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark TextAreaField Theme for all text areas. Used to make text area dark.
 *
 * @param <T> {@link TextAreaField} subclasses.
 */
public class FlatTextAreaFieldTheme<T extends TextAreaField> extends
    FlatBorderlessTransparentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatTextAreaFieldTheme() {
  }

  public FlatTextAreaFieldTheme(FlatColoredThemeSettings settings) {
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
    component.getFocusedStyle().getBackground()
        .setColor(settings.backgroundColor().mul(3)
            .add(ColorUtil.oppositeBlackOrWhite(settings.backgroundColor())).div(4));
    component.getStyle().setHighlightColor(settings.strokeColor());
    component.getStyle().getBackground().setColor(settings.backgroundColor());
  }
}

package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark TextInput Theme for all text inputs. Used to make text input dark.
 *
 * @param <T> {@link TextInput} subclasses.
 */
public class FlatTextInputTheme<T extends TextInput> extends FlatComponentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatTextInputTheme() {
  }

  public FlatTextInputTheme(FlatColoredThemeSettings settings) {
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

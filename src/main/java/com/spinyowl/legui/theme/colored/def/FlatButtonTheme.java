package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;
import org.joml.Vector4f;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatButtonTheme<T extends Button> extends FlatComponentTheme<T> {

  /** Default constructor. Settings should be specified before using this theme. */
  public FlatButtonTheme() {}

  public FlatButtonTheme(FlatColoredThemeSettings settings) {
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
    component.getStyle().getBackground().setColor(bgColor());

    component
        .getHoveredStyle()
        .getBackground()
        .setColor(bgColor().mul(3).add(ColorUtil.oppositeBlackOrWhite(bgColor())).div(4));
    component
        .getPressedStyle()
        .getBackground()
        .setColor(bgColor().mul(2).add(ColorUtil.oppositeBlackOrWhite(bgColor())).div(3));
    component.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
    component.getStyle().setBorder(null);
  }

  private Vector4f bgColor() {
    return settings.backgroundColor();
  }
}

package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.style.color.ColorUtil;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;
import org.joml.Vector4f;

/**
 * Dark Button Theme for all buttons. Used to make button dark.
 *
 * @param <T> {@link Button} subclasses.
 */
public class FlatPanelTheme<T extends Panel> extends FlatComponentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatPanelTheme() {
  }

  public FlatPanelTheme(FlatColoredThemeSettings settings) {
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
    Vector4f first = settings.backgroundColor();
    Vector4f second = ColorUtil.oppositeBlackOrWhite(settings.backgroundColor());
    Vector4f subtraction = new Vector4f(first).sub(second).mul(0.04F);
    first.sub(subtraction);

    component.getStyle().getBackground().setColor(first);
  }
}

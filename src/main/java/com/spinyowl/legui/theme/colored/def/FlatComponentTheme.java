package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Tooltip;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.shadow.Shadow;
import com.spinyowl.legui.theme.AbstractTheme;
import com.spinyowl.legui.theme.Themes;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;
import java.util.List;

/**
 * Dark Component Theme for all components. Used to make component dark.
 *
 * @param <T> {@link Component} subclasses.
 */
public class FlatComponentTheme<T extends Component> extends AbstractTheme<T> {

  protected FlatColoredThemeSettings settings;

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatComponentTheme() {
  }

  public FlatComponentTheme(FlatColoredThemeSettings settings) {
    this.settings = settings;
  }

  public FlatColoredThemeSettings getSettings() {
    return settings;
  }

  public void setSettings(FlatColoredThemeSettings settings) {
    this.settings = settings;
  }

  /**
   * Used to apply theme only for component and not apply for child components.
   *
   * @param component component to apply theme.
   */
  @Override
  public void apply(T component) {
    super.apply(component);
    if (settings.borderColor().z == 0) {
      component.getStyle().setBorder(null);
    } else {
      component.getStyle().setBorder(new SimpleLineBorder(settings.borderColor(), 1));
    }
    component.getStyle().setBorderRadius(1f);
    component.getStyle().getBackground().setColor(settings.backgroundColor());
    component.getStyle().setFocusedStrokeColor(settings.strokeColor());
    component.getStyle().setTextColor(settings.textColor());
    component.getStyle().setFontSize(settings.fontSize());
    component.getStyle().setFont(settings.font());
    component.getStyle().setHorizontalAlign(HorizontalAlign.LEFT);
    component.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);

    if (settings.shadowColor() != null && settings.shadowColor().length() > 0.00001f) {
      component.getStyle().setShadow(new Shadow(2, 2, 20, -8, settings.shadowColor()));
    } else {
      component.getStyle().setShadow(null);
    }

    Tooltip tooltip = component.getTooltip();
    if (tooltip != null) {
      Themes.getDefaultTheme().applyAll(tooltip);
    }
    List<? extends Component> childComponents = component.getChildComponents();
    for (Component child : childComponents) {
      Themes.getDefaultTheme().applyAll(child);
    }
  }
}

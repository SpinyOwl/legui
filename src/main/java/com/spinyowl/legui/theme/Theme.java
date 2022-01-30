package com.spinyowl.legui.theme;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Frame;

public abstract class Theme {

  private final ThemeManager themeManager;

  protected Theme(ThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  public ThemeManager getThemeManager() {
    return themeManager;
  }

  /**
   * Used to apply theme only to component and not apply to child components.
   *
   * @param component component to apply theme.
   */
  public <T extends Component> void apply(T component) {
    themeManager.getComponentTheme((Class<T>) component.getClass()).apply(component);
  }

  /**
   * Used to apply theme to component and to all children of this component. Should be reimplemented
   * for components that contains other child components.
   *
   * @param component component to apply theme.
   */
  public <T extends Component> void applyAll(T component) {
    themeManager.getComponentTheme((Class<T>) component.getClass()).applyAll(component);
  }

  /**
   * Used to apply theme to frame and to all children of this component. Should be reimplemented to
   * components that contains other child components.
   *
   * @param frame frame to apply theme.
   */
  public void applyAll(Frame frame) {
    frame.getAllLayers().forEach(this::applyAll);
  }
}

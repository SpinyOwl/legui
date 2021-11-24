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

  public <T extends Component> void apply(T component) {
    themeManager.getComponentTheme((Class<T>) component.getClass()).apply(component);
  }

  public <T extends Component> void applyAll(T component) {
    themeManager.getComponentTheme((Class<T>) component.getClass()).applyAll(component);
  }

  public void applyAll(Frame frame) {
    frame.getAllLayers().forEach(this::applyAll);
  }
}

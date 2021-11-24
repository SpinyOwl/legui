package com.spinyowl.legui.theme;

import com.spinyowl.legui.component.Component;


public abstract class ThemeManager {

  public abstract <T extends Component> AbstractTheme<T> getComponentTheme(Class<T> clazz);

  public abstract <T extends Component> void setComponentTheme(Class<T> clazz,
      AbstractTheme<T> theme);
}

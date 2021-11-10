package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.SelectBox;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;

/**
 * Dark SelectBoxScrollablePanel Theme.
 *
 * @param <T> {@link SelectBox.SelectBoxScrollablePanel} subclasses.
 */
public class FlatSelectBoxScrollablePanelTheme<T extends SelectBox.SelectBoxScrollablePanel> extends
    FlatScrollablePanelTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatSelectBoxScrollablePanelTheme() {
  }

  public FlatSelectBoxScrollablePanelTheme(FlatColoredThemeSettings settings) {
    super(settings);
  }

  @Override
  public void apply(T component) {
    super.apply(component);
    component.getStyle().getBackground().setColor(settings.backgroundColor());
  }
}

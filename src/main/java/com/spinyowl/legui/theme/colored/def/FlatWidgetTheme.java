package com.spinyowl.legui.theme.colored.def;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Widget;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.component.optional.align.VerticalAlign;
import com.spinyowl.legui.icon.CharIcon;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.theme.Themes;
import com.spinyowl.legui.theme.colored.FlatColoredTheme.FlatColoredThemeSettings;
import org.joml.Vector2f;

/**
 * Dark Widget Theme for all scrollable widgets. Used to make widget dark.
 *
 * @param <T> {@link Widget} subclasses.
 */
public class FlatWidgetTheme<T extends Widget> extends FlatComponentTheme<T> {

  /**
   * Default constructor. Settings should be specified before using this theme.
   */
  public FlatWidgetTheme() {
  }

  public FlatWidgetTheme(FlatColoredThemeSettings settings) {
    super(settings);
  }

  /**
   * Used to apply theme for component and for all children of this component. Should be
   * reimplemented for components that contains other child components.
   *
   * @param component component to apply theme.
   */
  @Override
  public void applyAll(T component) {
    super.apply(component);
    component.getStyle().getBackground().setColor(settings.backgroundColor());

    Button minimizeButton = component.getMinimizeButton();
    minimizeButton.getStyle().getBackground().setColor(settings.borderColor());
    minimizeButton.getStyle().setShadow(null);

    Button closeButton = component.getCloseButton();
    closeButton.getStyle().getBackground().setColor(settings.borderColor());
    closeButton.getStyle().setShadow(null);

    Component titleContainer = component.getTitleContainer();
    titleContainer.getStyle().getBackground().setColor(settings.borderColor());

    Button resizeButton = component.getResizeButton();
    resizeButton.getStyle().setBorder(null);
    resizeButton.getStyle().getBackground().setColor(ColorConstants.transparent());
    resizeButton.getHoveredStyle().getBackground().setColor(ColorConstants.transparent());
    resizeButton.getPressedStyle().getBackground().setColor(ColorConstants.transparent());

    CharIcon icon = new CharIcon(FontRegistry.MATERIAL_DESIGN_ICONS, '\uF45D');
    icon.setHorizontalAlign(HorizontalAlign.RIGHT);
    icon.setVerticalAlign(VerticalAlign.BOTTOM);
    icon.setColor(settings.textColor());
    resizeButton.getStyle().getBackground().setIcon(icon);
    resizeButton.getStyle().setShadow(null);

    component.getTitle().getStyle().setPadding(3f, 5f);

    Icon closeIcon = component.getCloseIcon();
    if (closeIcon instanceof CharIcon) {
      CharIcon bgIcon = (CharIcon) closeIcon;
      bgIcon.setColor(settings.textColor());
      bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
      bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
    }

    Icon minimizeIcon = component.getMinimizeIcon();
    if (minimizeIcon instanceof CharIcon) {
      CharIcon bgIcon = (CharIcon) minimizeIcon;
      bgIcon.setColor(settings.textColor());
      bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
      bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
    }

    Icon maximizeIcon = component.getMaximizeIcon();
    if (maximizeIcon instanceof CharIcon) {
      CharIcon bgIcon = (CharIcon) maximizeIcon;
      bgIcon.setColor(settings.textColor());
      bgIcon.setHorizontalAlign(HorizontalAlign.CENTER);
      bgIcon.setVerticalAlign(VerticalAlign.MIDDLE);
    }

    Themes.getDefaultTheme().applyAll(component.getContainer());
    component.getContainer().getStyle().setShadow(null);


  }
}

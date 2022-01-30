package com.spinyowl.legui.theme;

import static com.spinyowl.legui.style.color.ColorUtil.rgba;

import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.FontRegistry;
import com.spinyowl.legui.theme.colored.FlatColoredTheme;
import org.joml.Vector4f;

/**
 * Enum of existing themes.
 */
public final class Themes {

  public static final Theme FLAT_PETERRIVER = new FlatColoredTheme(
      rgba(41, 128, 185, 1), // backgroundColor
      rgba(52, 73, 94, 1), // borderColor
      rgba(52, 73, 94, 1), // sliderColor
      rgba(144, 164, 174, 1), // strokeColor
      rgba(46, 204, 113, 1), // allowColor
      rgba(231, 76, 60, 1), // denyColor
      rgba(0, 0, 0, 1f),  // shadowColor
      ColorConstants.black(),
      FontRegistry.getDefaultFont(),
      FlatColoredTheme.FONT_SIZE
  );
  public static final Theme FLAT_PETERRIVER_DARK = new FlatColoredTheme(
      rgba(44, 62, 80, 1), // backgroundColor
      rgba(127, 140, 141, 1), // borderColor
      rgba(127, 140, 141, 1), // sliderColor
      rgba(2, 119, 189, 1), // strokeColor
      rgba(39, 174, 96, 1), // allowColor
      rgba(192, 57, 43, 1), // denyColor
      rgba(0, 0, 0, 1f),  // shadowColor
      ColorConstants.white(),
      FontRegistry.getDefaultFont(),
      FlatColoredTheme.FONT_SIZE
  );
  public static final Theme FLAT_WHITE = new FlatColoredTheme(
      rgba(255, 255, 255, 1), // backgroundColor
      rgba(176, 190, 197, 1), // borderColor
      rgba(176, 190, 197, 1), // sliderColor
      rgba(100, 181, 246, 1), // strokeColor
      rgba(194, 219, 245, 1), // allowColor
      rgba(239, 154, 154, 1), // denyColor
      ColorConstants.black(),  // shadowColor
      ColorConstants.black(),
      FontRegistry.getDefaultFont(),
      FlatColoredTheme.FONT_SIZE
  );
  public static final Theme FLAT_DARK = new FlatColoredTheme(
      rgba(33, 33, 33, 1), // backgroundColor
      rgba(97, 97, 97, 1), // borderColor
      rgba(97, 97, 97, 1), // sliderColor
      rgba(2, 119, 189, 1), // strokeColor
      rgba(27, 94, 32, 1), // allowColor
      rgba(183, 28, 28, 1), // denyColor
      rgba(250, 250, 250, 0.5f),  // shadowColor
      ColorConstants.white(),
      FontRegistry.getDefaultFont(),
      FlatColoredTheme.FONT_SIZE
  );
  private static Theme DEFAULT_THEME = FLAT_WHITE;

  private Themes() {
  }

  public static Theme getDefaultTheme() {
    return DEFAULT_THEME;
  }

  public static void setDefaultTheme(Theme defaultTheme) {
    if (defaultTheme != null) {
      DEFAULT_THEME = defaultTheme;
    }
  }

}

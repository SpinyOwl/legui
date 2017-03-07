package org.liquidengine.legui.theme;

import org.joml.Vector4f;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;

/**
 * Created by ShchAlexander on 06.03.2017.
 */
public class DarkTheme extends Theme {
    @Override
    public Vector4f backgroundColor() {
        return ColorConstants.darkGray();
    }

    @Override
    public Border border() {
        return new SimpleLineBorder(ColorConstants.lightGray(), 1.2f);
    }

    @Override
    public String font() {
        return FontRegister.ROBOTO_REGULAR;
    }

    @Override
    public Vector4f fontColor() {
        return ColorConstants.white();
    }

    @Override
    public Vector4f highlightColor() {
        return ColorConstants.darkBlue();
    }

    @Override
    public Vector4f textPadding() {
        return new Vector4f(3, 5, 3, 5);
    }

    @Override
    public float fontSize() {
        return 16f;
    }

    @Override
    public HorizontalAlign horizontalAlign() {
        return HorizontalAlign.LEFT;
    }

    @Override
    public VerticalAlign verticalAlign() {
        return VerticalAlign.MIDDLE;
    }

    @Override
    public float cornerRadius() {
        return 2;
    }

    @Override
    public Vector4f scrollBarArrowColor() {
        return ColorConstants.white();
    }

    @Override
    public Vector4f scrollBarColor() {
        return ColorConstants.lightGray();
    }

    @Override
    public float scrollBarArrowSize() {
        return 10f;
    }

    @Override
    public boolean scrollBarArrowsEnabled() {
        return false;
    }

    @Override
    public Vector4f getFocusedStrokeColorLight() {
        return ColorConstants.blue();
    }
}

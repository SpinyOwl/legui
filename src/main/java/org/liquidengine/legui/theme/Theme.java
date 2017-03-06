package org.liquidengine.legui.theme;

import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.font.FontRegister;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public abstract class Theme {
    public static Theme DEFAULT_THEME = new DefaultTheme();

    public abstract Vector4f backgroundColor();

    public abstract Border border();

    public abstract String font();

    public abstract Vector4f fontColor();

    public abstract Vector4f highlightColor();

    public abstract Vector4f textPadding();

    public abstract float fontSize();

    public abstract HorizontalAlign horizontalAlign();

    public abstract VerticalAlign verticalAlign();

    public abstract float cornerRadius();

    public abstract Vector4f scrollBarArrowColor();

    public abstract Vector4f scrollBarColor();

    public abstract float scrollBarArrowSize();

    public abstract boolean scrollBarArrowsEnabled();

    public abstract Vector4f getFocusedStrokeColorLight();


    public static class DefaultTheme extends Theme {

        @Override
        public Border border() {
            return new SimpleLineBorder(ColorConstants.darkGray(), .7f);
        }

        @Override
        public String font() {
            return FontRegister.DEFAULT;
        }

        @Override
        public Vector4f fontColor() {
            return ColorConstants.black();
        }

        @Override
        public Vector4f highlightColor() {
            return ColorConstants.lightBlue();
        }

        @Override
        public Vector4f textPadding() {
            return new Vector4f(3);
        }

        @Override
        public float fontSize() {
            return 16;
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
            return ColorConstants.darkGray();
        }

        @Override
        public Vector4f scrollBarColor() {
            return ColorConstants.darkGray();
        }

        @Override
        public float scrollBarArrowSize() {
            return 30;
        }

        @Override
        public boolean scrollBarArrowsEnabled() {
            return true;
        }

        @Override
        public Vector4f getFocusedStrokeColorLight() {
            return new Vector4f(ColorConstants.lightBlue);
        }

        @Override
        public Vector4f backgroundColor() {
            return ColorConstants.white();
        }
    }
}

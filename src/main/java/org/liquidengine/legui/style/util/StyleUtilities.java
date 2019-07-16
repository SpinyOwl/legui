package org.liquidengine.legui.style.util;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.length.Length;

import static org.liquidengine.legui.style.length.LengthType.PERCENT;
import static org.liquidengine.legui.style.length.LengthType.PIXEL;

public final class StyleUtilities {
    private StyleUtilities(){}

    public static Vector4f getPaddingV4(Component component, Style style) {

        Length paddingLeft = style.getPaddingLeft();
        Length paddingRight = style.getPaddingRight();
        Length paddingTop = style.getPaddingTop();
        Length paddingBottom = style.getPaddingBottom();

        float parentWidth = 0;
        if (component.getParent() != null) {
            parentWidth = component.getParent().getSize().x;
        }

        return new Vector4f(
            getPadding(paddingLeft, parentWidth),
            getPadding(paddingTop, parentWidth),
            getPadding(paddingRight, parentWidth),
            getPadding(paddingBottom, parentWidth)
        );
    }


    private static float getPadding(Length padding, float parentWidth) {
        if (padding == null) return 0;
        else if (PIXEL.equals(padding.type())) {
            return PIXEL.type().cast(padding.get());
        } else if (PERCENT.equals(padding.type())) {
            return PERCENT.type().cast(padding.get()) * parentWidth;
        }
        return 0;
    }
}

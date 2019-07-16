package org.liquidengine.legui.style.util;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.length.Length;

import static org.liquidengine.legui.style.length.LengthType.PERCENT;
import static org.liquidengine.legui.style.length.LengthType.PIXEL;

public final class StyleUtilities {
    private StyleUtilities() {
    }

    /**
     * Used to get component padding represented with {@link Vector4f}.
     *
     * @param component component for which padding is calculated.
     * @param style     style with paddings.
     * @return padding represented with {@link Vector4f} where x - left, y - top, z - right, w - bottom.
     */
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

    /**
     * Used to extract float value of padding from {@link Length} value.
     *
     * @param padding     padding
     * @param parentWidth parent width to calculate padding using percentage.
     * @return float(pixels) representation of padding.
     */
    private static float getPadding(Length padding, float parentWidth) {
        if (padding == null) return 0;
        else if (PIXEL.equals(padding.type())) {
            return PIXEL.type().cast(padding.get());
        } else if (PERCENT.equals(padding.type())) {
            return PERCENT.type().cast(padding.get()) * parentWidth;
        }
        return 0;
    }


    /**
     * Used to calculate inner content rectangle (box-sizing: border-box)
     *
     * @param componentPosition component position.
     * @param componentSize     component size.
     * @param componentPadding  component padding.
     * @return inner content rectangle represented with {@link Vector4f} where x,y - position, z,w - width and height.
     */
    public static Vector4f getInnerContentRectangle(Vector2f componentPosition,
                                                    Vector2f componentSize,
                                                    Vector4f componentPadding) {
        return new Vector4f(
            componentPosition.x + componentPadding.x,
            componentPosition.y + componentPadding.y,
            componentSize.x - componentPadding.x - componentPadding.z,
            componentSize.y - componentPadding.y - componentPadding.w
        );
    }
}

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
    public static Vector4f getPadding(Component component, Style style) {

        Length paddingLeft = style.getPaddingLeft();
        Length paddingRight = style.getPaddingRight();
        Length paddingTop = style.getPaddingTop();
        Length paddingBottom = style.getPaddingBottom();

        float baseWidth = 0;
        if (component.getParent() != null) {
            baseWidth = component.getParent().getSize().x;
        } else {
            baseWidth = component.getSize().x;
        }

        return new Vector4f(
            getFloatLength(paddingLeft, baseWidth),
            getFloatLength(paddingTop, baseWidth),
            getFloatLength(paddingRight, baseWidth),
            getFloatLength(paddingBottom, baseWidth)
        );
    }

    /**
     * Used to extract float value of length from {@link Length} value.
     *
     * @param length     length
     * @param baseWidth base width to calculate length using percentage.
     * @return float(pixels) representation of length.
     */
    public static float getFloatLength(Length length, float baseWidth) {
        if (length == null) return 0;
        else if (PIXEL.equals(length.type())) {
            return PIXEL.type().cast(length.get());
        } else if (PERCENT.equals(length.type())) {
            return PERCENT.type().cast(length.get()) * baseWidth;
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

    /**
     * Returns vector of four border radius elements where: x = top left, y = top right, z = bottom right, w = bottom left.
     * <p>
     * NOTE. IF radius specified in percents - radius will be calculated using only width of component - will be represented with segment of circle (not ellipse).
     * </p>
     *
     * @return vector of four border radius.
     */
    public static Vector4f getBorderRadius(Component component, Style style) {
        Length borderTopLeftRadius = style.getBorderTopLeftRadius();
        Length borderTopRightRadius = style.getBorderTopRightRadius();
        Length borderBottomRightRadius = style.getBorderBottomRightRadius();
        Length borderBottomLeftRadius = style.getBorderBottomLeftRadius();

        return new Vector4f(
            getFloatLength(borderTopLeftRadius, component.getSize().x),
            getFloatLength(borderTopRightRadius, component.getSize().x),
            getFloatLength(borderBottomRightRadius, component.getSize().x),
            getFloatLength(borderBottomLeftRadius, component.getSize().x)
        );
    }

}

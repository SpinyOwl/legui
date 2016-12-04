package org.liquidengine.legui.component.intersector;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.PolygonsIntersection;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;

import static org.liquidengine.legui.util.Util.calculatePosition;

/**
 * Created by Shcherbin Alexander on 6/21/2016.
 */
public class RectangleIntersector implements LeguiIntersector {
    float paddingLeft;
    float paddingRight;
    float paddingTop;
    float paddingBottom;

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public float getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public float getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    public float getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    @Override
    public boolean intersects(Component gui, Vector2f point) {
        Vector2f pos = calculatePosition(gui);
        float x = pos.x;
        float y = pos.y;
        float w = gui.getSize().x;
        float h = gui.getSize().y;
        float verticies[] = {
                x - paddingLeft, y - paddingTop,
                x + w + paddingRight, y - paddingTop,
                x + w + paddingRight, y + h + paddingBottom,
                x - paddingLeft, y + h + paddingBottom
        };
        int start[] = {0};
        int count = 4;
        PolygonsIntersection intersector = new PolygonsIntersection(verticies, start, count);
        return intersector.testPoint(point.x, point.y);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("paddingLeft", paddingLeft)
                .append("paddingRight", paddingRight)
                .append("paddingTop", paddingTop)
                .append("paddingBottom", paddingBottom)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RectangleIntersector that = (RectangleIntersector) o;

        return new EqualsBuilder()
                .append(paddingLeft, that.paddingLeft)
                .append(paddingRight, that.paddingRight)
                .append(paddingTop, that.paddingTop)
                .append(paddingBottom, that.paddingBottom)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(paddingLeft)
                .append(paddingRight)
                .append(paddingTop)
                .append(paddingBottom)
                .toHashCode();
    }
}

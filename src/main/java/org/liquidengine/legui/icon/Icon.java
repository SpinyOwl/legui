package org.liquidengine.legui.icon;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;

/**
 * Icon. Used to draw component icons.
 */
public abstract class Icon {
    private Vector2f size;
    private HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;
    private VerticalAlign   verticalAlign   = VerticalAlign.MIDDLE;

    public Icon() {
        size = new Vector2f();
    }

    public Icon(Vector2f size) {
        setSize(size);
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        if (size != null) this.size = size;
        else this.size = new Vector2f();
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Icon icon = (Icon) o;

        return new EqualsBuilder()
                .append(getSize(), icon.getSize())
                .append(getHorizontalAlign(), icon.getHorizontalAlign())
                .append(getVerticalAlign(), icon.getVerticalAlign())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getSize())
                .append(getHorizontalAlign())
                .append(getVerticalAlign())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("size", size)
                .append("horizontalAlign", horizontalAlign)
                .append("verticalAlign", verticalAlign)
                .toString();
    }
}

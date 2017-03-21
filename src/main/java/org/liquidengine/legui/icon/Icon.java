package org.liquidengine.legui.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;

/**
 * Icon. Used to draw component icons.
 */
public abstract class Icon {
    /**
     * Icon size.
     */
    private Vector2f size;

    /**
     * Icon horizontal alignment in component.
     */
    private HorizontalAlign horizontalAlign = HorizontalAlign.CENTER;

    /**
     * Icon vertical alignment in component.
     */
    private VerticalAlign verticalAlign = VerticalAlign.MIDDLE;

    /**
     * Default constructor.
     */
    public Icon() {
        size = new Vector2f();
    }

    /**
     * Used to create icon with defined size.
     *
     * @param size size to set.
     */
    public Icon(Vector2f size) {
        setSize(size);
    }

    /**
     * Returns size of icon.
     *
     * @return size of icon.
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Used to set size of icon.
     *
     * @param size size to set.
     */
    public void setSize(Vector2f size) {
        if (size != null) this.size = size;
        else this.size = new Vector2f();
    }

    /**
     * Returns horizontal alignment in component.
     *
     * @return horizontal alignment in component.
     */
    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    /**
     * Used to set horizontal align of icon.
     *
     * @param horizontalAlign horizontal align to set.
     */
    public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    /**
     * Returns vertical alignment in component.
     *
     * @return vertical alignment in component.
     */
    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * Used to set vertical align of icon.
     *
     * @param verticalAlign vertical align to set.
     */
    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }
}

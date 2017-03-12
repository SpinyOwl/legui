package org.liquidengine.legui.icon;

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
}

package org.liquidengine.legui.border;

import org.joml.Vector4f;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class SimpleLineBorder extends Border {
    private float    thickness;
    private Vector4f color;

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }
}

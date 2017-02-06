package org.liquidengine.legui.border;

import org.joml.Vector4f;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
 */
public class SimpleLineBorder extends Border {
    private float    thickness;
    private Vector4f color;

    public SimpleLineBorder(){
        thickness = 0;
        color = ColorConstants.transparent();
    }

    public SimpleLineBorder(Vector4f color, float thickness) {
        this.thickness = thickness;
        this.color = color;
    }

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

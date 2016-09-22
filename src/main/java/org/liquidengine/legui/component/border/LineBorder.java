package org.liquidengine.legui.component.border;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.util.ColorConstants;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class LineBorder extends Border {
    private Vector4f borderColor;
    private float thickness;
    private float borderRadius;

    public LineBorder(Component component, Vector4f borderColor, float thickness) {
        super(component);
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    public LineBorder(Component component) {
        this(component, ColorConstants.black(), 1);
    }

    public Vector4f getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Vector4f borderColor) {
        this.borderColor = borderColor;
    }


    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }
}

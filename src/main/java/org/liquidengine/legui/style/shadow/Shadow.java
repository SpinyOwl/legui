package org.liquidengine.legui.style.shadow;

import org.joml.Vector4f;
import org.liquidengine.legui.style.color.ColorConstants;

/**
 * Created by ShchAlexander on 10.05.2018.
 */
public class Shadow {

    private float hOffset;
    private float vOffset;
    private float blur;
    private float spread;
    private Vector4f color = ColorConstants.transparent();

    public Shadow() {
    }

    public Shadow(float hOffset, float vOffset, float blur, float spread, Vector4f color) {
        this.hOffset = hOffset;
        this.vOffset = vOffset;
        this.blur = blur;
        this.spread = spread;
        this.color = color;
    }

    public float gethOffset() {
        return hOffset;
    }

    public void sethOffset(float hOffset) {
        this.hOffset = hOffset;
    }

    public float getvOffset() {
        return vOffset;
    }

    public void setvOffset(float vOffset) {
        this.vOffset = vOffset;
    }

    public float getBlur() {
        return blur;
    }

    public void setBlur(float blur) {
        if (blur > 0) {
            this.blur = blur;
        }
    }

    public float getSpread() {
        return spread;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }
}

package org.liquidengine.legui.render.nvg.border;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiBorderRenderer;
import org.liquidengine.legui.util.ColorUtil;
import org.liquidengine.legui.util.NvgRenderUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.Util.calculatePosition;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class NvgSimpleLineBorderRenderer extends NvgLeguiBorderRenderer {
    private NVGColor colorA = NVGColor.create();

    @Override
    public void render(Border border, LeguiContext context, Component component, long nvgContext) {
        if (border.isEnabled()) {
            SimpleRectangleLineBorder simpleRectangleLineBorder = (SimpleRectangleLineBorder) border;

            boolean hovered = component.getState().isHovered();
            boolean pressed = component.getState().isPressed();

            Vector2f pos  = calculatePosition(component);
            Vector2f size = component.getSize();

            Vector4f borderColor  = simpleRectangleLineBorder.getBorderColor();
            float    cornerRadius = component.getCornerRadius();
            float    thickness    = simpleRectangleLineBorder.getThickness();

            NvgRenderUtils.drawRectStroke(nvgContext, pos, size, borderColor, cornerRadius, thickness);
            if (hovered && !pressed) {
                Vector4f color = ColorUtil.oppositeBlackOrWhite(borderColor);
                color.mul(0.2f).add(new Vector4f(borderColor).mul(0.2f));
                NvgRenderUtils.drawRectStroke(nvgContext, pos.x - 1.5f, pos.y - 1.5f, size.x + 3, size.y + 3, color, cornerRadius, 1.5f);
            } else if (hovered && pressed) {
                Vector4f color = ColorUtil.oppositeBlackOrWhite(borderColor);
                color.mul(0.2f).add(new Vector4f(borderColor).mul(0.2f));
                NvgRenderUtils.drawRectStroke(nvgContext, pos.x - 1.5f, pos.y - 1.5f, size.x + 3, size.y + 3, color, cornerRadius, 1.5f);
            }
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NvgSimpleLineBorderRenderer that = (NvgSimpleLineBorderRenderer) o;

        return new EqualsBuilder()
                .append(colorA, that.colorA)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(colorA)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("colorA", colorA)
                .toString();
    }
}

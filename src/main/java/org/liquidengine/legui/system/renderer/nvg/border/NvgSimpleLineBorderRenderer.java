package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgBorderRenderer;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtil.drawRectStroke;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgSimpleLineBorderRenderer extends NvgBorderRenderer<SimpleLineBorder> {
    @Override
    protected void renderBorder(SimpleLineBorder border, Component component, Context context, long nanovg) {
        if (border.isEnabled()) {
            boolean hovered = component.isHovered();
            boolean pressed = component.isPressed();

            Vector2f pos  = component.getScreenPosition();
            Vector2f size = component.getSize();

            Vector4f borderColor  = border.getColor();
            float    cornerRadius = component.getCornerRadius();
            float    thickness    = border.getThickness();

            drawRectStroke(nanovg, pos, size, borderColor, cornerRadius, thickness);
            if (hovered && !pressed) {
                Vector4f color = ColorUtil.oppositeBlackOrWhite(borderColor);
                color.mul(0.2f).add(new Vector4f(borderColor).mul(0.2f));
                drawRectStroke(nanovg, pos.x - 1.5f, pos.y - 1.5f, size.x + 3, size.y + 3, color, cornerRadius, 1.5f);
            } else if (hovered && pressed) {
                Vector4f color = ColorUtil.oppositeBlackOrWhite(borderColor);
                color.mul(0.2f).add(new Vector4f(borderColor).mul(0.2f));
                drawRectStroke(nanovg, pos.x - 1.5f, pos.y - 1.5f, size.x + 3, size.y + 3, color, cornerRadius, 1.5f);
            }
        }
    }
}

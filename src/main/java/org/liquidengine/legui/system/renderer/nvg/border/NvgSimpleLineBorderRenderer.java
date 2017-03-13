package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgBorderRenderer;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.drawRectStroke;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgSimpleLineBorderRenderer extends NvgBorderRenderer<SimpleLineBorder> {
    @Override
    protected void renderBorder(SimpleLineBorder border, Component component, Context context, long nanovg) {
        if (border.isEnabled()) {
            if (border.getThickness() <= 0 || border.getColor().w == 0) return;

            Vector2f pos  = component.getScreenPosition();
            Vector2f size = component.getSize();

            Vector4f borderColor  = border.getColor();
            float    cornerRadius = component.getCornerRadius();
            float    thickness    = border.getThickness();

            drawRectStroke(nanovg, pos, size, borderColor, cornerRadius, thickness);
        }
    }
}

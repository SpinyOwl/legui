package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgSimpleLineBorderRenderer extends NvgBorderRenderer<SimpleLineBorder> {

    @Override
    protected void renderBorder(SimpleLineBorder border, Component component, Context context, long nanovg) {
        if (border.isEnabled()) {
            float thickness = border.getThickness();
            Vector4f borderColor = border.getColor();
            if (thickness <= 0 || borderColor.w == 0) {
                return;
            }

//            float cornerRadius = component.getCornerRadius();
            Vector2f size = component.getSize();
            Style style = component.getStyle();
            if (component.isFocused() && style.getFocusedStrokeColor() != null) {
                Vector4f strokeColor = style.getFocusedStrokeColor();
                Vector2f pos = component.getAbsolutePosition();
                NvgShapes.drawRectStroke(nanovg, pos.add(-1f, +1f), size, strokeColor, 1f, style.getCornerRadius());
            }

            NvgShapes.drawRectStroke(
                nanovg, component.getAbsolutePosition(), size, borderColor, thickness, style.getCornerRadius());

        }
    }
}

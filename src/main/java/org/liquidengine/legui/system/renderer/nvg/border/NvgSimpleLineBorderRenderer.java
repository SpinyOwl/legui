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

//            float cornerRadius = component.getBorderRadius();
            Vector2f size = component.getSize();
            Style style = component.getStyle();
            Vector2f absolutePosition = component.getAbsolutePosition();
            if (component.isFocused() && style.getFocusedStrokeColor() != null) {
                Vector4f strokeColor = style.getFocusedStrokeColor();
                Vector2f pos = new Vector2f(absolutePosition);
                NvgShapes.drawRectStroke(nanovg, pos.add(-0.5f, +0.5f), size, strokeColor, 1f, style.getBorderRadius());
            }

            Vector2f bSize = new Vector2f(size);
            bSize.add(thickness, thickness);
            Vector2f bPos = new Vector2f(absolutePosition).sub(thickness / 2f, thickness / 2f);
            NvgShapes.drawRectStroke(nanovg, bPos, bSize, borderColor, thickness, style.getBorderRadius());

        }
    }
}

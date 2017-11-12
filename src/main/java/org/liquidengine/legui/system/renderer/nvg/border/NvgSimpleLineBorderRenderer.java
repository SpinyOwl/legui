package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
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

            float cornerRadius = component.getCornerRadius();
            Vector2f size = component.getSize();
            if (component.isFocused()) {
                NvgShapes.drawRectStroke(
                    nanovg, component.getAbsolutePosition().add(-0.5f, +0.5f), size, component.getFocusedStrokeColor(), 0.5f, cornerRadius);
            }
            NvgShapes.drawRectStroke(
                nanovg, component.getAbsolutePosition(), size, borderColor, thickness, cornerRadius);
        }
    }
}

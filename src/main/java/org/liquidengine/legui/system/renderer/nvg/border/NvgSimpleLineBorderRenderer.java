package org.liquidengine.legui.system.renderer.nvg.border;

import org.liquidengine.legui.border.SimpleLineBorder;
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
            if (border.getThickness() <= 0 || border.getColor().w == 0) {
                return;
            }

            NvgShapes.drawRectStroke(
                nanovg, component.getScreenPosition(), component.getSize(), border.getColor(), border.getThickness(), component.getCornerRadius());
        }
    }
}

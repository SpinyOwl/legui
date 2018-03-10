package org.liquidengine.legui.system.renderer.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.style.Border;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgDefaultBorderRenderer extends NvgBorderRenderer {

    @Override
    protected void renderBorder(Border border, Component component, Context context, long nanovg) {
        if (!component.isVisible()) {
            return;
        }
        // render simple rectangle border
        Vector2f position = component.getAbsolutePosition();
        Vector2f size = component.getSize();

        float x = position.x;
        float y = position.y;
        float w = size.x;
        float h = size.y;

        NvgShapes.drawRectStroke(nanovg, new Vector4f(x, y, w, h), ColorConstants.black, 1);

        if (component.isFocused()) {
            NvgShapes.drawRectStroke(nanovg, new Vector4f(x - 1, y - 1, w + 2, h + 2), ColorConstants.red, 2);
        }
    }
}

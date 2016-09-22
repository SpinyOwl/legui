package org.liquidengine.legui.render.nvg.border;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.LineBorder;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiBorderRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class NvgLineBorderRenderer extends NvgLeguiBorderRenderer {
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Border border, LeguiContext context, long nvgContext) {
        LineBorder lineBorder = (LineBorder) border;
        Component component = border.getComponent();

        Vector2f pos = calculatePosition(component);
        Vector2f size = component.getSize();

        Vector4f color = lineBorder.getBorderColor();
        float thickness = lineBorder.getThickness();
        float borderRadius = lineBorder.getBorderRadius();

        nvgBeginPath(nvgContext);
        nvgStrokeWidth(nvgContext, thickness);
        nvgRoundedRect(nvgContext, pos.x, pos.y, size.x, size.y, borderRadius);
        nvgStrokeColor(nvgContext, rgba(color, colorA));
        nvgStroke(nvgContext);
    }
}

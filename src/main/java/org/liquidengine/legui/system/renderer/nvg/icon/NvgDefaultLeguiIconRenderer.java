package org.liquidengine.legui.system.renderer.nvg.icon;

import org.joml.Vector2f;
import org.liquidengine.legui.color.ColorConstants;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.LeguiContext;
import org.liquidengine.legui.system.renderer.nvg.NvgLeguiIconRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.drawRectStroke;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgDefaultLeguiIconRenderer extends NvgLeguiIconRenderer {
    private NVGColor def;

    @Override
    public void initialize() {
        def = NVGColor.calloc();
        def.r(1);
        def.g(0);
        def.b(0);
        def.a(1);
    }

    @Override
    public void destroy() {
        def.free();
    }

    @Override
    protected void renderIcon(Icon icon, Component component, LeguiContext context, long nanovg) {
        if (!component.isVisible()) return;
        // render simple rectangle border
        Vector2f position = component.getScreenPosition();
        Vector2f size     = component.getSize();
        Vector2f iconSize = icon.getSize();

        float x = position.x + icon.getHorizontalAlign().index * (size.x - iconSize.x) / 2f;
        float y = position.y + icon.getVerticalAlign().index * (size.y - iconSize.y) / 2f;
        float w = iconSize.x;
        float h = iconSize.y;


        nvgBeginPath(nanovg);
        nvgFillColor(nanovg, def);
        nvgRect(nanovg, x, y, w, h);
        nvgFill(nanovg);

        drawRectStroke(nanovg, x, y, w, h, ColorConstants.black, 1, 1);
    }
}

package org.liquidengine.legui.render.nvg;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.util.NVGUtils;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgDefaultRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void render(Component gui, LeguiContext leguiContext, long context) {
        nvgSave(context);
        Vector2f pos = calculatePosition(gui);
        float x = pos.x;
        float y = pos.y;
        float w = gui.getSize().x;
        float h = gui.getSize().y;
        Vector4f backgroundColor = new Vector4f(gui.getBackgroundColor());

        // rectangle
        nvgBeginPath(context);
        nvgRoundedRect(context, x, y, w, h, 0);
        nvgFillColor(context, NVGUtils.rgba(backgroundColor, colorA));
        nvgFill(context);

        Border border = gui.getBorder();
        if (border != null) {
            border.render(gui, leguiContext);
        }
    }

}

package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/23/2016.
 */
public class NvgButtonRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA = NVGColor.calloc();
    private NVGColor colorB = NVGColor.calloc();
    private NVGPaint paintA = NVGPaint.calloc();

    @Override
    public void render(Component component, LeguiContext context, long nvgContext) {
        createScissor(nvgContext, component);
        {
            Button agui = (Button) component;
            Vector2f pos = Util.calculatePosition(component);
            Vector2f size = component.getSize();

            Border border = agui.getBorder();

            nvgSave(nvgContext);
            // render background
            {
                Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());
                if (agui.isHovered()) backgroundColor.w *= 0.5f;
                if (agui.isPressed()) {backgroundColor.mul(0.5f); backgroundColor.w = 1; }

                nvgBeginPath(nvgContext);
                nvgFillColor(nvgContext, rgba(backgroundColor, colorA));
                nvgRoundedRect(nvgContext, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
                nvgFill(nvgContext);

                nvgLinearGradient(nvgContext, pos.x, pos.y, pos.x, pos.y + size.y, rgba(1f, 1f, 1f, 0.2f, colorA), rgba(0f, 0f, 0f, 0.2f, colorB), paintA);
                nvgFillPaint(nvgContext, paintA);
                nvgFill(nvgContext);
            }

            // Render text
            {
                TextState textState = agui.getTextState();
                renderTextStateLineToBounds(nvgContext, pos, size, textState);
            }

            // Render border
            if (border != null) {
                border.render(context);
            }

            nvgRestore(nvgContext);

        }
        resetScissor(nvgContext);
    }
}

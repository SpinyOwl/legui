package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgButtonRenderer extends NvgDefaultComponentRenderer<Button> {

    @Override
    protected void renderSelf(Button button, Context context, long nanovg) {
        createScissor(nanovg, button);
        {
            Vector2f pos = button.getAbsolutePosition();
            Vector2f size = button.getSize();

            // render background
            renderBackground(button, context, nanovg);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            NvgText.drawTextLineToRect(nanovg, button.getTextState(), pos, size, true, getFont(button), getFontSize(button));

        }
        resetScissor(nanovg);
    }

}

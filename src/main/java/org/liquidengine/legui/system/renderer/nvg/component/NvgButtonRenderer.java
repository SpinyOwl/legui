package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.style.Style;
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
            Style style = button.getStyle();
            Vector2f pos = button.getAbsolutePosition();
            Vector2f size = button.getSize();

            // render background
            renderBackground(button, context, nanovg);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            TextState text = button.getTextState();
            Vector4f rect = new Vector4f(pos, ((Vector2fc) size).x(), ((Vector2fc) size).y());
            NvgText.drawTextLineToRect(nanovg, (Vector4fc) rect, true, style.getHorizontalAlign(), style.getVerticalAlign(),
                    getFontSize(button), getFont(button), text.getText(), style.getTextColor());

        }
        resetScissor(nanovg);
    }

}

package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.style.util.StyleUtilities.getStyle;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgButtonRenderer extends NvgDefaultComponentRenderer<Button> {

    @Override
    protected void renderSelf(Button component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            Vector2f pos = component.getAbsolutePosition();
            Vector2f size = component.getSize();

            // render background
            renderBackground(component, context, nanovg);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            TextState text = component.getTextState();
            Vector4f rect = new Vector4f(pos, size.x(), size.y());
            NvgText.drawTextLineToRect(nanovg, rect, true,
                    getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT),
                    getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE),
                    getStyle(component, Style::getFontSize, 16F),
                    getStyle(component, Style::getFont, FontRegistry.getDefaultFont()),
                    text.getText(),
                    getStyle(component, Style::getTextColor));

        }
        resetScissor(nanovg);
    }

}

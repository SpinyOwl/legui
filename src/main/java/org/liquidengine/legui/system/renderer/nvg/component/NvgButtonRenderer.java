package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.event.button.ButtonWidthChangeEvent;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.listener.processor.EventProcessorProvider;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;

import static org.liquidengine.legui.style.util.StyleUtilities.getStyle;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.calculateTextBoundsRect;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgText.drawTextLineToRect;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

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

            // Render text
            TextState textState = component.getTextState();
            Vector4f rect = new Vector4f(pos, size.x(), size.y());
            HorizontalAlign horizontalAlign = getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT);
            VerticalAlign verticalAlign = getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE);
            Float fontSize = getStyle(component, Style::getFontSize, 16F);

            float[] textBounds = calculateTextBoundsRect(nanovg, rect, textState.getText(), horizontalAlign, verticalAlign, fontSize);
            float textWidth = textState.getTextWidth();

            if (Math.abs(textWidth - textBounds[2]) > 0.001) {
                EventProcessorProvider.getInstance().pushEvent(new ButtonWidthChangeEvent(component, context, component.getFrame(), textBounds[2]));
            }

            pos = component.getAbsolutePosition();
            size = component.getSize();

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            textState = component.getTextState();
            rect = new Vector4f(pos, size.x(), size.y());
            horizontalAlign = getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT);
            verticalAlign = getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE);
            fontSize = getStyle(component, Style::getFontSize, 16F);

            textState.setTextWidth(textBounds[2]);
            textState.setTextHeight(fontSize);
            textState.setCaretX(null);
            textState.setCaretY(null);

            renderBackground(component, context, nanovg);
            drawTextLineToRect(nanovg, rect, true,
                    horizontalAlign, verticalAlign, fontSize,
                    getStyle(component, Style::getFont, FontRegistry.getDefaultFont()),
                    textState.getText(),
                    getStyle(component, Style::getTextColor),
                    component.getTextDirection());
        }
        resetScissor(nanovg);
    }
}

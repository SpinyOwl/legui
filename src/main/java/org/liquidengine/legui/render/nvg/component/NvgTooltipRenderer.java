package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.util.NVGUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Aliaksandr_Shcherbin on 1/27/2017.
 */
public class NvgTooltipRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA = NVGColor.create();


    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {

        createScissor(context, component);
        {
            Tooltip         tooltip         = (Tooltip) component;
            TextState       textState       = tooltip.getTextState();
            Vector2f        pos             = calculatePosition(tooltip.getTooltipComponent()).add(tooltip.getPosition());
            Vector2f        size            = component.getSize();
            float           fontSize        = textState.getFontSize();
            String          font            = textState.getFont();
            String          text            = textState.getText();
            HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
            VerticalAlign   verticalAlign   = textState.getVerticalAlign();
            Vector4f        textColor       = textState.getTextColor();
            Vector4f        backgroundColor = component.getBackgroundColor();
            Vector4f        padding         = new Vector4f(textState.getPadding());

            renderBackground(component, context, pos, size, backgroundColor);

            nvgFontSize(context, fontSize);
            nvgFontFace(context, font);

            ByteBuffer byteText = null;
            try {

                byteText = memUTF8(text, false);
                long start      = memAddress(byteText);
                long end        = start + byteText.remaining();
                int  rows       = 0;
                int  currentRow = 0;

                float x = pos.x + padding.x;
                float y = pos.y + padding.y;
                float w = size.x - padding.x - padding.z;
                float h = size.y - padding.y - padding.w;

                intersectScissor(context, new Vector4f(x, y, w, h));

                List<float[]> boundList   = new ArrayList<>();
                List<long[]>  indicesList = new ArrayList<>();

                alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
                nvgFontSize(context, fontSize);
                nvgFontFace(context, font);
                nvgFillColor(context, rgba(textColor, colorA));

                // calculate text bounds for every line and start/end indices
                NVGTextRow.Buffer buffer = NVGTextRow.calloc(1);
                while ((rows = nnvgTextBreakLines(context, start, end, size.x, memAddress(buffer), 1)) != 0) {
                    NVGTextRow row    = buffer.get(0);
                    float[]    bounds = createBounds(x, y + currentRow * fontSize, w, h, horizontalAlign, verticalAlign, row.width(), fontSize);
                    boundList.add(bounds);
                    indicesList.add(new long[]{row.start(), row.end()});
                    start = row.next();
                    currentRow++;
                }
                rows = currentRow;

                // calculate offset for all lines
                float offsetY = 0.5f * fontSize * ((rows - 1) * verticalAlign.index - 1);

                // render text lines
                nvgFillColor(context, rgba(textColor, colorA));
                for (int i = 0; i < rows; i++) {
                    float[] bounds  = boundList.get(i);
                    long[]  indices = indicesList.get(i);

                    nvgBeginPath(context);
                    nnvgText(context, bounds[4], bounds[5] - offsetY, indices[0], indices[1]);
                }

                buffer.free();
            } finally {
                memFree(byteText);
            }
        }
        resetScissor(context);
        createScissor(context, component);
        {
            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderBackground(Component component, long context, Vector2f pos, Vector2f size, Vector4f backgroundColor) {
        // render background rectangle
        NVGColor nvgColor = NVGColor.calloc();
        NVGColor rgba     = NVGUtils.rgba(backgroundColor, nvgColor);
        nvgBeginPath(context);
        nvgFillColor(context, rgba);
        nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
        nvgFill(context);
        nvgColor.free();
    }
}

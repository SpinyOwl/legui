package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.font.FontRegistry;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgColorUtil;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGTextRow;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.liquidengine.legui.style.util.StyleUtilities.getPadding;
import static org.liquidengine.legui.style.util.StyleUtilities.getStyle;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by ShchAlexander on 13.02.2017.
 */
public class NvgTooltipRenderer extends NvgDefaultComponentRenderer<Tooltip> {


    @Override
    public void renderSelf(Tooltip component, Context context, long nanovg) {
        createScissor(nanovg, component);
        {
            Style style = component.getStyle();
            TextState textState = component.getTextState();
            Vector2f pos = component.getAbsolutePosition();
            Vector2f size = component.getSize();
            float fontSize = getStyle(component, Style::getFontSize, 16F);
            String font = getStyle(component, Style::getFont, FontRegistry.DEFAULT);
            String text = textState.getText();
            HorizontalAlign horizontalAlign = getStyle(component, Style::getHorizontalAlign, HorizontalAlign.LEFT);
            VerticalAlign verticalAlign = getStyle(component, Style::getVerticalAlign, VerticalAlign.MIDDLE);
            Vector4f textColor = getStyle(component, Style::getTextColor);
            Vector4f padding = getPadding(component, style);

            renderBackground(component, context, nanovg);

            nvgFontSize(nanovg, fontSize);
            nvgFontFace(nanovg, font);

            ByteBuffer byteText = null;
            try {

                byteText = memUTF8(text, false);
                long start = memAddress(byteText);
                long end = start + byteText.remaining();

                float x = pos.x + padding.x;
                float y = pos.y + padding.y;
                float w = size.x - padding.x - padding.z;
                float h = size.y - padding.y - padding.w;

                intersectScissor(nanovg, new Vector4f(x, y, w, h));

                List<float[]> boundList = new ArrayList<>();
                List<long[]> indicesList = new ArrayList<>();

                try (
                        NVGColor colorA = NVGColor.calloc();
                        NVGTextRow.Buffer buffer = NVGTextRow.calloc(1)
                ) {
                    NvgColorUtil.fillNvgColorWithRGBA(textColor, colorA);
                    alignTextInBox(nanovg, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
                    nvgFontSize(nanovg, fontSize);
                    nvgFontFace(nanovg, font);
                    nvgFillColor(nanovg, colorA);

                    // calculate text bounds for every line and start/end indices

                    int rows = 0;
                    while (nnvgTextBreakLines(nanovg, start, end, size.x, memAddress(buffer), 1) != 0) {
                        NVGTextRow row = buffer.get(0);
                        float[] bounds = createBounds(x, y + rows * fontSize, w, h, horizontalAlign, verticalAlign, row.width(), fontSize);
                        boundList.add(bounds);
                        indicesList.add(new long[]{row.start(), row.end()});
                        start = row.next();
                        rows++;
                    }

                    // calculate offset for all lines
                    float offsetY = 0.5f * fontSize * ((rows - 1) * verticalAlign.index - 1);

                    // render text lines
                    NvgColorUtil.fillNvgColorWithRGBA(textColor, colorA);
                    nvgFillColor(nanovg, colorA);
                    for (int i = 0; i < rows; i++) {
                        float[] bounds = boundList.get(i);
                        long[] indices = indicesList.get(i);

                        nvgBeginPath(nanovg);
                        nnvgText(nanovg, bounds[4], bounds[5] - offsetY, indices[0], indices[1]);
                    }
                }
            } finally {
                memFree(byteText);
            }
        }
        resetScissor(nanovg);
    }
}

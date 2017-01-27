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
import org.liquidengine.legui.util.ColorConstants;
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

//            Vector2f pos             = calculatePosition(component);
//            Vector2f size            = component.getSize();
//            Vector4f backgroundColor = new Vector4f(component.getBackgroundColor());

            /*Draw background rectangle*/
//            {
//                nvgBeginPath(context);
//                nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, 0);
//                nvgFillColor(context, rgba(backgroundColor, colorA));
//                nvgFill(context);
//            }

            // draw text
            renderText(((Tooltip) component), context);
//            renderTextStateLineToBounds(context, pos, size, textState, false);

            renderBorder(component, leguiContext);
        }
        resetScissor(context);
    }

    private void renderText(Tooltip component, long context) {
        TextState       textState       = component.getTextState();
        Vector2f        pos             = calculatePosition(component);
        Vector2f        size            = component.getSize();
        float           fontSize        = textState.getFontSize();
        String          font            = textState.getFont();
        String          text            = textState.getText();
        HorizontalAlign horizontalAlign = textState.getHorizontalAlign();
        VerticalAlign   verticalAlign   = textState.getVerticalAlign();
        Vector4f        textColor       = textState.getTextColor();
        Vector4f        backgroundColor = component.getBackgroundColor();
        Vector4f        padding         = new Vector4f(textState.getPadding());


        float offsetX = horizontalAlign.index * 0.5f * size.x;
        float offsetY = verticalAlign.index * 0.5f * size.y - fontSize / 2;

        nvgFontSize(context, fontSize);
        nvgFontFace(context, font);

        ByteBuffer byteText = null;
        try {
            alignTextInBox(context, horizontalAlign, verticalAlign);

            byteText = memUTF8(text, false);
            long start      = memAddress(byteText);
            long end        = start + byteText.remaining();
            int  rows       = 0;
            int  currentRow = 0;

            float x = pos.x + padding.x;
            float y = pos.y + padding.y;
            float w = size.x - padding.x - padding.z;
            float h = size.y - padding.y - padding.w;

            List<float[]> boundList   = new ArrayList<>();
            List<long[]>  indicesList = new ArrayList<>();

            alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
            nvgFontSize(context, fontSize);
            nvgFontFace(context, font);
            nvgFillColor(context, rgba(textColor, colorA));


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
            NVGColor nvgColor = NVGColor.calloc();
            NVGColor rgba     = NVGUtils.rgba(backgroundColor, nvgColor);
            nvgBeginPath(context);
            nvgFillColor(context, rgba);
            nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
            nvgFill(context);
            nvgColor.free();

            nvgFillColor(context, rgba(textColor, colorA));
            for (int i = 0; i < rows ; i++) {
                float[] bounds  = boundList.get(i);
                long[]  indices = indicesList.get(i);

                drawRectStroke(context, bounds[0], bounds[1], bounds[2], bounds[3], ColorConstants.red, 0, 1);
                drawRectStroke(context, bounds[4], bounds[5], bounds[6], bounds[7], ColorConstants.green, 0, 1);

                nvgBeginPath(context);
                nnvgText(context, bounds[4], bounds[1] - offsetY + fontSize * currentRow * verticalAlign.index / 2f, indices[0], indices[1]);
            }

//                alignTextInBox(context, HorizontalAlign.LEFT, VerticalAlign.MIDDLE);
//                NVGTextRow row    = buffer.get(0);
//                float[]    bounds = createBounds(pos.x, pos.y + currentRow * fontSize, size.x, fontSize, horizontalAlign, verticalAlign, row.width(), fontSize);
//
//                NVGColor nvgColor = NVGColor.calloc();
//                NVGColor rgba     = NVGUtils.rgba(backgroundColor, nvgColor);
//                nvgBeginPath(context);
//                nvgFillColor(context, rgba);
//                nvgRoundedRect(context, pos.x, pos.y + currentRow * fontSize, size.x, fontSize, component.getCornerRadius());
//                nvgFill(context);
//                nvgColor.free();
//
//                nvgBeginPath(context);
//                NVGColor textColorN = textColor.w == 0 ? NVGUtils.rgba(0.0f, 0.0f, 0.0f, 1f, colorA) : NVGUtils.rgba(textColor, colorA);
//                nvgFillColor(context, textColorN);
//                nnvgText(context, bounds[0], bounds[1], row.start(), row.end());
//
//                currentRow++;
//                start = row.next();

            buffer.free();
        } finally {
            memFree(byteText);
        }
    }
}

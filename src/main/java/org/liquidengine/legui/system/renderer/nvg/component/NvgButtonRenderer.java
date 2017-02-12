package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.NvgImageReferenceManager;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.IMAGE_REFERENCE_MANAGER;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgButtonRenderer extends NvgComponentRenderer<Button> {
    private NVGColor colorA     = NVGColor.calloc();
    private NVGPaint imagePaint = NVGPaint.calloc();

    @Override
    protected void renderComponent(Button button, Context context, long nanovg) {
        createScissor(nanovg, button);
        {
            Vector2f pos  = button.getScreenPosition();
            Vector2f size = button.getSize();

            NvgImageReferenceManager manager = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
            nvgSave(nanovg);
            // render background
            renderBackground(nanovg, button, pos, size, manager);

            // Render text
            {
                TextState textState = button.getTextState();
                nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
                renderTextStateLineToBounds(nanovg, pos, size, textState);
            }

        }
        resetScissor(nanovg);

        renderBorderWScissor(button, context, nanovg);


        nvgRestore(nanovg);
    }

    private void renderBackground(long context, Button button, Vector2f pos, Vector2f size, NvgImageReferenceManager manager) {
        boolean  focused         = button.isFocused();
        boolean  hovered         = button.isHovered();
        boolean  pressed         = button.isPressed();
        Vector4f backgroundColor = new Vector4f(button.getBackgroundColor());

        ImageView bgImage = button.getBackgroundImage();
        ImageView image   = null;
        if (!focused && !hovered && !pressed) {
            image = bgImage;
        } else if (hovered && !pressed) {
            image = (image = button.getHoveredBackgroundImage()) == null ? bgImage : image;
        } else if (pressed) {
            image = (image = button.getPressedBackgroundImage()) == null ? bgImage : image;
        } else if (focused) {
            image = (image = button.getFocusedBbackgroundImage()) == null ? bgImage : image;
        }

        drawRectangle(context, backgroundColor, pos, size);
        if (hovered) {
            if (!pressed) {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.3f;
                drawRectangle(context, opp, pos, size);
            } else if (pressed) {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.6f;
                drawRectangle(context, opp, pos, size);
            }
        }
        if (image != null) {
            drawImage(context, pos, manager, image);
        }
    }

    private void drawImage(long context, Vector2f pos, NvgImageReferenceManager manager, ImageView image) {
        Vector2f bipos    = image.getPosition();
        Vector2f bisize   = image.getSize();
        int      imageRef = manager.getImageReference(image.getImage(), context);

        float ox = pos.x + bipos.x;
        float oy = pos.y + bipos.y;
        nvgBeginPath(context);
        nvgImagePattern(context, ox, oy, bisize.x, bisize.y, 0, imageRef, 1, imagePaint);
        nvgRoundedRect(context, ox, oy, bisize.x, bisize.y, image.getCornerRadius());
        nvgFillPaint(context, imagePaint);
        nvgFill(context);
    }
}

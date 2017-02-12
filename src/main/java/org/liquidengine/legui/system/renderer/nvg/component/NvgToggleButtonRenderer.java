package org.liquidengine.legui.system.renderer.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.ToggleButton;
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
public class NvgToggleButtonRenderer extends NvgComponentRenderer<ToggleButton> {
    private NVGColor colorA     = NVGColor.calloc();
    private NVGPaint imagePaint = NVGPaint.calloc();

    @Override
    protected void renderComponent(ToggleButton toggleButton, Context context, long nanovg) {
        createScissor(nanovg, toggleButton);
        {
            Vector2f pos  = toggleButton.getScreenPosition();
            Vector2f size = toggleButton.getSize();

            NvgImageReferenceManager manager = (NvgImageReferenceManager) context.getContextData().get(IMAGE_REFERENCE_MANAGER);
            nvgSave(nanovg);
            // render background
            renderBackground(nanovg, toggleButton, pos, size, manager);
            renderBorder(toggleButton, context);
        }
        resetScissor(nanovg);

        nvgRestore(nanovg);
    }

    private void renderBackground(long context, ToggleButton agui, Vector2f pos, Vector2f size, NvgImageReferenceManager manager) {
        boolean  focused         = agui.isFocused();
        boolean  hovered         = agui.isHovered();
        boolean  pressed         = agui.isPressed();
        boolean  toggled         = agui.isToggled();
        Vector4f backgroundColor = new Vector4f(toggled ? agui.getToggledBackgroundColor() : agui.getBackgroundColor());

        ImageView bgImage = toggled ? agui.getTogglededBackgroundImage() : agui.getBackgroundImage();
        ImageView image   = null;
        if (!focused && !hovered && !pressed) {
            image = bgImage;
        } else if (hovered && !pressed) {
            image = (image = agui.getHoveredBackgroundImage()) == null ? bgImage : image;
        } else if (pressed) {
            image = (image = agui.getPressedBackgroundImage()) == null ? bgImage : image;
        } else if (focused) {
            image = (image = agui.getFocusedBbackgroundImage()) == null ? bgImage : image;
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

    @Override
    public void destroy() {
        super.destroy();
        colorA.free();
        imagePaint.free();
    }
}

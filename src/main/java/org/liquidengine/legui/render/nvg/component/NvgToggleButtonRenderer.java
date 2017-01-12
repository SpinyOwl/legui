package org.liquidengine.legui.render.nvg.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiComponentRenderer;
import org.liquidengine.legui.render.nvg.NvgLeguiRenderer;
import org.liquidengine.legui.render.nvg.image.NvgImageReferenceManager;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.*;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Alexander on 12.01.2017.
 */
public class NvgToggleButtonRenderer extends NvgLeguiComponentRenderer {
    private NVGColor colorA     = NVGColor.calloc();
    private NVGPaint imagePaint = NVGPaint.calloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            ToggleButton agui = (ToggleButton) component;
            Vector2f     pos  = Util.calculatePosition(component);
            Vector2f     size = component.getSize();

            NvgImageReferenceManager manager = (NvgImageReferenceManager) leguiContext.getContextData().get(NvgLeguiRenderer.IMAGE_REFERENCE_MANAGER);
            nvgSave(context);
            // render background
            renderBackground(context, agui, pos, size, manager);
        }
        resetScissor(context);

        createScissor(context, component);
        {
            // Render border
            renderBorder(component, leguiContext);
        }
        resetScissor(context);

        nvgRestore(context);
    }

    private void renderBackground(long context, ToggleButton agui, Vector2f pos, Vector2f size, NvgImageReferenceManager manager) {
        boolean  focused         = agui.getState().isFocused();
        boolean  hovered         = agui.getState().isHovered();
        boolean  pressed         = agui.getState().isPressed();
        boolean  toggled         = agui.isToggled();
        Vector4f backgroundColor = new Vector4f(toggled ? agui.getToggledBackgroundColor() : agui.getBackgroundColor());

        ImageView bgImage = toggled ? agui.getTogglededBackgroundImage() : agui.getBackgroundImage();
        ImageView image   = null;
        if (!focused && !hovered && !pressed) {
            image = bgImage;
        } else if (hovered && !pressed) {
            backgroundColor.w *= 0.5f;
            image = agui.getHoveredBackgroundImage();
            if (image == null) {
                image = bgImage;
            }
        } else if (pressed) {
            backgroundColor.mul(0.5f);
            backgroundColor.w = 1;
            image = agui.getPressedBackgroundImage();
            if (image == null) {
                image = agui.getHoveredBackgroundImage();
                if (image == null) {
                    image = bgImage;
                }
            }
        } else if (focused) {
            image = agui.getFocusedBbackgroundImage();
            if (image == null) {
                image = bgImage;
            }
        }

        drawColoredRect(context, agui, pos, size, backgroundColor);
        if (image != null) {
            drawImage(context, pos, manager, image);
        }
    }

    private void drawColoredRect(long context, ToggleButton agui, Vector2f pos, Vector2f size, Vector4f backgroundColor) {
        nvgBeginPath(context);
        nvgFillColor(context, rgba(backgroundColor, colorA));
        nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, agui.getCornerRadius());
        nvgFill(context);
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

    private void drawBackgroundImage(ImageView image, long context, Vector2f pos, Vector2f size) {

    }

    private void drawBackgroundColor(long context, ToggleButton agui, Vector2f pos, Vector2f size) {
        Vector4f backgroundColor = new Vector4f(agui.getBackgroundColor());
        if (agui.getState().isHovered()) backgroundColor.mul(0.5f);
        if (agui.getState().isPressed()) {
            backgroundColor.mul(0.5f);
            backgroundColor.w = 1;
        }

        drawColoredRect(context, agui, pos, size, backgroundColor);
    }

    @Override
    public void destroy() {
        super.destroy();
        colorA.free();
        imagePaint.free();
    }
}
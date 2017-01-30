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
import org.liquidengine.legui.util.ColorUtil;
import org.liquidengine.legui.util.Util;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

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
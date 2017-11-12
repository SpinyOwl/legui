package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgButtonRenderer extends NvgDefaultComponentRenderer<Button> {

    @Override
    protected void renderSelf(Button button, Context context, long nanovg) {
        createScissor(nanovg, button);
        {
            Vector2f pos = button.getAbsolutePosition();
            Vector2f size = button.getSize();

            // render background
            renderBackground(nanovg, button, pos, size, context);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            NvgText.drawTextLineToRect(nanovg, button.getTextState(), pos, size, true);

        }
        resetScissor(nanovg);
    }

    private void renderBackground(long nvg, Button button, Vector2f pos, Vector2f size, Context context) {
        boolean focused = button.isFocused();
        boolean hovered = button.isHovered();
        boolean pressed = button.isPressed();
        Vector4f backgroundColor = new Vector4f(button.getBackgroundColor());

        Icon bgIcon = button.getBackgroundIcon();
        Icon image;
        if (!focused && !hovered && !pressed) {
            image = bgIcon;
        } else if (hovered && !pressed) {
            image = (image = button.getHoveredBackgroundIcon()) == null ? bgIcon : image;
        } else if (pressed) {
            image = (image = button.getPressedBackgroundIcon()) == null ? bgIcon : image;
        } else {
            image = (image = button.getFocusedBackgroundIcon()) == null ? bgIcon : image;
        }

        nvgSave(nvg);
        NvgShapes.drawRect(nvg, pos, size, backgroundColor, button.getCornerRadius());
        if (hovered) {
            if (!pressed) {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.3f;
                NvgShapes.drawRect(nvg, pos, size, opp, button.getCornerRadius());
            } else {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.6f;
                NvgShapes.drawRect(nvg, pos, size, opp, button.getCornerRadius());
            }
        }
        if (image != null) {
            renderIcon(image, button, context);
        }
        nvgRestore(nvg);
    }

}

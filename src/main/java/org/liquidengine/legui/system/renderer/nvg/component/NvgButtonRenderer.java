package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.Style;
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

        Style style = button.getStyle();
        Style currStyle = button.getStyle();

        Icon bgIcon = style.getBackground().getIcon();
        Vector4f bgColor = style.getBackground().getColor();
        Vector4f cornerRadius = style.getBorderRadius();

        if(focused) {
            currStyle = button.getFocusedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
            if (currStyle.getBorderRadius() != null) {
                cornerRadius = currStyle.getBorderRadius();
            }
        }
        if(hovered) {
            currStyle = button.getHoveredStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
            if (currStyle.getBorderRadius() != null) {
                cornerRadius = currStyle.getBorderRadius();
            }
        }
        if(pressed) {
            currStyle = button.getPressedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                bgIcon = currStyle.getBackground().getIcon();
            }
            if (currStyle.getBorderRadius() != null) {
                cornerRadius = currStyle.getBorderRadius();
            }
        }

        nvgSave(nvg);
        NvgShapes.drawRect(nvg, pos, size, bgColor, cornerRadius);
        if (bgIcon != null) {
            renderIcon(bgIcon, button, context);
        }
        nvgRestore(nvg);
    }

}

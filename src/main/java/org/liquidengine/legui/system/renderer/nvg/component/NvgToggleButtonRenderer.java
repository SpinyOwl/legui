package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissorByParent;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgIntersectScissor;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;
import org.liquidengine.legui.system.renderer.nvg.util.NvgText;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgToggleButtonRenderer extends NvgDefaultComponentRenderer<ToggleButton> {

    @Override
    protected void renderSelf(ToggleButton toggleButton, Context context, long nanovg) {
        NvgRenderUtils.createScissor(nanovg, toggleButton);
        {
            Vector2f pos = toggleButton.getAbsolutePosition();
            Vector2f size = toggleButton.getSize();

            // render background
            renderBackground(nanovg, toggleButton, pos, size, context);

            // Render text
            nvgIntersectScissor(nanovg, pos.x, pos.y, size.x, size.y);
            NvgText.drawTextLineToRect(nanovg, toggleButton.getTextState(), pos, size, true);
        }
        resetScissor(nanovg);
    }

    private void renderBackground(long nvg, ToggleButton agui, Vector2f pos, Vector2f size, Context context) {
        boolean focused = agui.isFocused();
        boolean hovered = agui.isHovered();
        boolean pressed = agui.isPressed();

        Style style = agui.getStyle();
        Style currStyle = agui.getStyle();

        Icon icon = style.getBackground().getIcon();
        Vector4f bgColor = style.getBackground().getColor();
        Vector4f cornerRadius = getBorderRadius(agui);

        if(focused) {
            currStyle = agui.getFocusedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }
        if(hovered) {
            currStyle = agui.getHoveredStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }
        if(pressed) {
            currStyle = agui.getPressedStyle();
            if (currStyle.getBackground().getColor() != null) {
                bgColor = currStyle.getBackground().getColor();
            }
            if (currStyle.getBackground().getIcon() != null) {
                icon = currStyle.getBackground().getIcon();
            }
        }

        NvgRenderUtils.renderShadow(nvg, agui);

        boolean toggled = agui.isToggled();
        if (toggled) {
            NvgShapes.drawRect(nvg, pos, size, agui.getToggledBackgroundColor(), cornerRadius);
        } else {
            NvgShapes.drawRect(nvg, pos, size, bgColor, cornerRadius);
        }
        if (icon != null) {
            createScissorByParent(nvg, agui);
            renderIcon(icon, agui, context);
        }
    }

}

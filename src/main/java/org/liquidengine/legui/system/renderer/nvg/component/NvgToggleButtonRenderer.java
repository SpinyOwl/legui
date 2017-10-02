package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissorByParent;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.color.ColorUtil;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgToggleButtonRenderer extends NvgComponentRenderer<ToggleButton> {

    @Override
    protected void renderComponent(ToggleButton toggleButton, Context context, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, toggleButton, () -> {
            Vector2f pos = toggleButton.getAbsolutePosition();
            Vector2f size = toggleButton.getSize();

            // render background
            renderBackground(nanovg, toggleButton, pos, size, context);
            renderBorder(toggleButton, context);
        });
    }

    private void renderBackground(long nvg, ToggleButton agui, Vector2f pos, Vector2f size, Context context) {
        boolean focused = agui.isFocused();
        boolean hovered = agui.isHovered();
        boolean pressed = agui.isPressed();
        boolean toggled = agui.isToggled();
        Vector4f backgroundColor = new Vector4f(toggled ? agui.getToggledBackgroundColor() : agui.getBackgroundColor());

        Icon icon = getIcon(agui, focused, hovered, pressed, toggled);

        NvgShapes.drawRect(nvg, pos, size, backgroundColor, agui.getCornerRadius());
        if (hovered) {
            if (!pressed) {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.3f;
                NvgShapes.drawRect(nvg, pos, size, opp, agui.getCornerRadius());
            } else {
                Vector4f opp = ColorUtil.oppositeBlackOrWhite(backgroundColor);
                opp.w = 0.6f;
                NvgShapes.drawRect(nvg, pos, size, opp, agui.getCornerRadius());
            }
        }

        if (icon != null) {
            createScissorByParent(nvg, agui);
            renderIcon(icon, agui, context);
        }
    }

    private Icon getIcon(ToggleButton agui, boolean focused, boolean hovered, boolean pressed, boolean toggled) {
        Icon icon;
        Icon togglededBackgroundIcon = agui.getTogglededBackgroundIcon();
        Icon bgIcon = agui.getBackgroundIcon();
        if (toggled && togglededBackgroundIcon != null) {
            bgIcon = togglededBackgroundIcon;
        }
        if (!focused && !hovered && !pressed) {
            icon = bgIcon;
        } else if (hovered && !pressed) {
            icon = (icon = agui.getHoveredBackgroundIcon()) == null ? bgIcon : icon;
        } else if (pressed) {
            icon = (icon = agui.getPressedBackgroundIcon()) == null ? bgIcon : icon;
        } else {
            icon = (icon = agui.getFocusedBackgroundIcon()) == null ? bgIcon : icon;
        }
        return icon;
    }
}

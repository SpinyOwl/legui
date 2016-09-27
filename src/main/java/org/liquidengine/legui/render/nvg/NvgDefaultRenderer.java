package org.liquidengine.legui.render.nvg;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ComponentContainer;
import org.liquidengine.legui.component.ContainerHolder;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.nanovg.NVGColor;

import java.util.List;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.util.NvgRenderUtils.resetScissor;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgDefaultRenderer extends NvgLeguiComponentRenderer {
    private static final Vector4f BLACK = ColorConstants.black();
    private NVGColor colorA = NVGColor.malloc();

    @Override
    public void render(Component component, LeguiContext leguiContext, long context) {
        createScissor(context, component);
        {
            nvgSave(context);

            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();
            Vector4f backgroundColor =component.getBackgroundColor();

            // rectangle
            nvgBeginPath(context);
            nvgRoundedRect(context, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
            nvgFillColor(context, rgba(backgroundColor, colorA));
            nvgFill(context);

            Border border = component.getBorder();
            if (border != null) {
                border.render(leguiContext);
            }
        }
        resetScissor(context);

        if (component instanceof ContainerHolder) {
            ComponentContainer container = ((ContainerHolder) component).getContainer();
            List<Component> components = container.getComponents();
            components.stream().filter(Component::isVisible).forEach(child -> child.render(leguiContext));
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

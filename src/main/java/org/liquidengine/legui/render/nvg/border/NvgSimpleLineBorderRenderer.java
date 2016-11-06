package org.liquidengine.legui.render.nvg.border;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joml.Vector2f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleLineBorder;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiBorderRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class NvgSimpleLineBorderRenderer extends NvgLeguiBorderRenderer {
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Border border, LeguiContext context, Component component, long nvgContext) {
        if (border.isEnabled()) {
            SimpleLineBorder simpleLineBorder = (SimpleLineBorder) border;

            Vector2f pos = calculatePosition(component);
            Vector2f size = component.getSize();

            nvgBeginPath(nvgContext);
            nvgStrokeWidth(nvgContext, simpleLineBorder.getThickness());
            nvgStrokeColor(nvgContext, rgba(simpleLineBorder.getBorderColor(), colorA));
            nvgRoundedRect(nvgContext, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
            nvgStroke(nvgContext);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NvgSimpleLineBorderRenderer that = (NvgSimpleLineBorderRenderer) o;

        return new EqualsBuilder()
                .append(colorA, that.colorA)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(colorA)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("colorA", colorA)
                .toString();
    }
}

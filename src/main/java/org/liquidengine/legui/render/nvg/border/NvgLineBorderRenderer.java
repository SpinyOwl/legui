package org.liquidengine.legui.render.nvg.border;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.LineBorder;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.nvg.NvgLeguiBorderRenderer;
import org.lwjgl.nanovg.NVGColor;

import static org.liquidengine.legui.util.NVGUtils.rgba;
import static org.liquidengine.legui.util.Util.calculatePosition;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public class NvgLineBorderRenderer extends NvgLeguiBorderRenderer {
    private NVGColor colorA = NVGColor.calloc();

    @Override
    public void render(Border border, LeguiContext context, long nvgContext) {
        LineBorder lineBorder = (LineBorder) border;
        Component component = border.getComponent();

        Vector2f pos = calculatePosition(component);
        Vector2f size = component.getSize();

        Vector4f color = lineBorder.getBorderColor();
        float thickness = lineBorder.getThickness();

        nvgBeginPath(nvgContext);
        nvgStrokeWidth(nvgContext, thickness);
        nvgRoundedRect(nvgContext, pos.x, pos.y, size.x, size.y, component.getCornerRadius());
        nvgStrokeColor(nvgContext, rgba(color, colorA));
        nvgStroke(nvgContext);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
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

package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.NvgRenderer.renderBorder;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.NvgComponentRenderer;
import org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgProgressBarRenderer extends NvgComponentRenderer<ProgressBar> {

    @Override
    public void renderComponent(ProgressBar progressBar, Context leguiContext, long nanovg) {
        NvgRenderUtils.drawInScissor(nanovg, progressBar, () -> {
            nvgSave(nanovg);
            Vector2f pos = progressBar.getAbsolutePosition();
            float cornerRadius = progressBar.getCornerRadius();

            Vector2f size = progressBar.getSize();
            Vector4f backgroundColor = progressBar.getBackgroundColor();
            NvgShapes.drawRect(nanovg, pos, size, backgroundColor, cornerRadius);

            Vector4f progressColor = progressBar.getProgressColor();
            Vector2f progressSize = new Vector2f(size.x * progressBar.getValue() / ProgressBar.MAX_VALUE, size.y);
            NvgShapes.drawRect(nanovg, pos, progressSize, progressColor, cornerRadius);
        });
    }
}

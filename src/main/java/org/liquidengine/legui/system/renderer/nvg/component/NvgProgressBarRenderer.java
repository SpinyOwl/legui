package org.liquidengine.legui.system.renderer.nvg.component;

import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static org.liquidengine.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.renderer.nvg.util.NvgShapes;

/**
 * Created by ShchAlexander on 11.02.2017.
 */
public class NvgProgressBarRenderer extends NvgDefaultComponentRenderer<ProgressBar> {

    @Override
    public void renderSelf(ProgressBar progressBar, Context leguiContext, long nanovg) {
        createScissor(nanovg, progressBar);
        {
            nvgSave(nanovg);
            Vector2f pos = progressBar.getAbsolutePosition();
            Vector4f cornerRadius = progressBar.getStyle().getCornerRadius();

            Vector2f size = progressBar.getSize();
            Vector4f backgroundColor = progressBar.getStyle().getBackground().getColor();
            NvgShapes.drawRect(nanovg, pos, size, backgroundColor, cornerRadius);

            Vector4f progressColor = progressBar.getProgressColor();
            Vector2f progressSize = new Vector2f(size.x * progressBar.getValue() / ProgressBar.MAX_VALUE, size.y);
            NvgShapes.drawRect(nanovg, pos, progressSize, progressColor, cornerRadius);
        }
        resetScissor(nanovg);
    }
}

package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import com.spinyowl.legui.component.ProgressBar;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class NvgProgressBarRenderer extends NvgDefaultComponentRenderer<ProgressBar> {

  @Override
  public void renderSelf(ProgressBar progressBar, Context leguiContext, long nanovg) {
    createScissor(nanovg, progressBar);
    {
      nvgSave(nanovg);
      Vector2f pos = progressBar.getAbsolutePosition();
      Vector4f cornerRadius = getBorderRadius(progressBar);
      Vector2f size = progressBar.getSize();

      renderBackground(progressBar, leguiContext, nanovg);

      Vector4f progressColor = progressBar.getProgressColor();
      Vector2f progressSize = new Vector2f(size.x * progressBar.getValue() / ProgressBar.MAX_VALUE,
          size.y);
      NvgShapes.drawRect(nanovg, pos, progressSize, progressColor, cornerRadius);
    }
    resetScissor(nanovg);
  }
}

package com.spinyowl.legui.system.renderer.nvg;

import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.NVG_CONTEXT;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.style.Border;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.BorderRenderer;


public abstract class NvgBorderRenderer<B extends Border> extends BorderRenderer<B> {

  @Override
  public void renderBorder(B border, Component component, Context context) {
    long nanovgContext = (long) context.getContextData().get(NVG_CONTEXT);
    if (!border.isEnabled()) {
      return;
    }
    renderBorder(border, component, context, nanovgContext);
  }

  protected abstract void renderBorder(B border, Component component, Context context, long nanovg);

}

package com.spinyowl.legui.system.renderer.nvg.component;

import static com.spinyowl.legui.style.util.StyleUtilities.getStyle;
import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderBorderWScissor;
import static com.spinyowl.legui.system.renderer.nvg.NvgRenderer.renderIcon;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.createScissor;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.getBorderRadius;
import static com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils.resetScissor;
import static org.lwjgl.nanovg.NanoVG.nvgRestore;
import static org.lwjgl.nanovg.NanoVG.nvgSave;

import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.system.context.Context;
import com.spinyowl.legui.system.renderer.RendererProvider;
import com.spinyowl.legui.system.renderer.nvg.NvgComponentRenderer;
import com.spinyowl.legui.system.renderer.nvg.util.NvgRenderUtils;
import com.spinyowl.legui.system.renderer.nvg.util.NvgShapes;
import org.joml.Vector4f;

/**
 * Default component renderer.
 *
 * @param <C> component type.
 */
public class NvgDefaultComponentRenderer<C extends Component> extends NvgComponentRenderer<C> {

  /**
   * Used to render component.
   *
   * @param component component to render.
   * @param context   legui context.
   * @param nanovg    nanovg context pointer.
   */
  @Override
  protected void renderComponent(C component, Context context, long nanovg) {
    if (component.isVisible() && component.getSize().lengthSquared() > 0.01) {
      renderSelf(component, context, nanovg);
      renderChildComponents(component, context, nanovg);
      renderBorder(component, context, nanovg);
    }
  }

  /**
   * Used to render component without childComponents.
   *
   * @param component component to render.
   * @param context   context.
   * @param nanovg    nanovg context pointer.
   */
  protected void renderSelf(C component, Context context, long nanovg) {
    createScissor(nanovg, component);
    {
      renderBackground(component, context, nanovg);
    }
    resetScissor(nanovg);
  }

  protected void renderBackground(C component, Context context, long nanovg) {
    Icon bgIcon = getStyle(component, s -> s.getBackground().getIcon());
    Vector4f bgColor = getStyle(component, s -> s.getBackground().getColor());
    Vector4f cornerRadius = getBorderRadius(component);

    NvgRenderUtils.renderShadow(nanovg, component);

    nvgSave(nanovg);
    NvgShapes.drawRect(nanovg, component.getAbsolutePosition(), component.getSize(), bgColor,
        cornerRadius);
    if (bgIcon != null) {
      renderIcon(bgIcon, component, context);
    }
    nvgRestore(nanovg);
  }

  /**
   * Used to render component childComponents.
   *
   * @param component component to render.
   * @param context   context.
   * @param nanovg    nanovg context pointer.
   */
  protected void renderChildComponents(C component, Context context, long nanovg) {
    for (Component child : component.getChildComponents()) {
      RendererProvider.getInstance().getComponentRenderer(child.getClass()).render(child, context);
    }
  }

  /**
   * Used to render component border.
   *
   * @param component component to render.
   * @param context   context.
   * @param nanovg    nanovg context pointer.
   */
  protected void renderBorder(C component, Context context, long nanovg) {
    renderBorderWScissor(component, context, nanovg);
  }
}

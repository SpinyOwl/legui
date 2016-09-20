package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiComponentRenderer;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public abstract class NvgLeguiComponentRenderer implements LeguiComponentRenderer {

    @Override
    public final void render(Component gui, LeguiContext context) {
        render(gui, context, (Long) context.getContextData().get("nvgContext"));
    }

    public abstract void render(Component component, LeguiContext context, long nvgContext);

}

package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;
import org.liquidengine.legui.render.LeguiBorderRenderer;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public abstract class NvgLeguiBorderRenderer implements LeguiBorderRenderer {

    @Override
    public final void render(Border border, LeguiContext context) {
        render(border, context, (Long) context.getContextData().get("nvgContext"));
    }

    public abstract void render(Border border, LeguiContext context, long nvgContext);

}

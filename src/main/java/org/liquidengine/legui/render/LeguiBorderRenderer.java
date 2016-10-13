package org.liquidengine.legui.render;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.context.LeguiContext;

/**
 * Created by Shcherbin Alexander on 9/22/2016.
 */
public interface LeguiBorderRenderer {
    /**
     * Initialize renderer
     */
    void initialize();

    /**
     * Render Gui and fit it to target width and height
     *
     * @param border gui component to render
     */
    void render(Border border, LeguiContext context, Component component);

    /**
     * Destroy renderer
     */
    void destroy();
}

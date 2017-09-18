package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;

/**
 * Created by ShchAlexander on 19.09.2017.
 */
public interface Renderer {

    void initialize();

    void render(Frame frame, Context context);

    void destroy();
}

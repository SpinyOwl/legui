package org.liquidengine.legui.system.renderer;

import org.liquidengine.legui.component.Component;

/**
 * Created by Aliaksandr_Shcherbin on 1/24/2017.
 */
public interface LeguiComponentRenderer<C extends Component> {
    void initialize();

    void render(C component);

    void destroy();
}

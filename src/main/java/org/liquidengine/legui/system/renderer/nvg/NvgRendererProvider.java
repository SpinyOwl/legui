package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.system.renderer.LeguiComponentRenderer;
import org.liquidengine.legui.system.renderer.LeguiRendererProvider;

import java.util.List;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgRendererProvider extends LeguiRendererProvider {

    @Override
    public <C extends Component> LeguiComponentRenderer getComponentRenderer(Class<C> componentClass) {
        return null;
    }

    @Override
    public List<LeguiComponentRenderer> getComponentRenderers() {
        return null;
    }
}

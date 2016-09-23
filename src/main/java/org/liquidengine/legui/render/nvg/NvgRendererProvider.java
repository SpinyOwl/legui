package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Image;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.LineBorder;
import org.liquidengine.legui.render.LeguiBorderRenderer;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;
import org.liquidengine.legui.render.nvg.border.NvgLineBorderRenderer;
import org.liquidengine.legui.render.nvg.component.NvgButtonRenderer;
import org.liquidengine.legui.render.nvg.component.NvgImageRenderer;
import org.liquidengine.legui.render.nvg.component.NvgLabelRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgRendererProvider extends LeguiRendererProvider {
    private Map<Class<? extends Component>, LeguiComponentRenderer> rendererMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Border>, LeguiBorderRenderer> borderRendererMap = new ConcurrentHashMap<>();
    private NvgDefaultRenderer renderer = new NvgDefaultRenderer();
    private NvgLeguiBorderRenderer borderRenderer = new NvgLineBorderRenderer();

    public NvgRendererProvider() {
        initialize();
    }

    private void initialize() {

        // components
        registerRenderer(Label.class, new NvgLabelRenderer());
        registerRenderer(Image.class, new NvgImageRenderer());
        registerRenderer(Button.class, new NvgButtonRenderer());

        // borders
        registerRenderer(LineBorder.class, new NvgLineBorderRenderer());

    }

    @Override
    public LeguiComponentRenderer getRenderer(Component component) {
        return rendererMap.getOrDefault(component.getClass(), renderer);
    }

    @Override
    public LeguiBorderRenderer getRenderer(Border border) {
        return borderRendererMap.getOrDefault(border.getClass(), borderRenderer);
    }

    @Override
    public List<LeguiComponentRenderer> getComponentRenderers() {
        return new ArrayList<>(rendererMap.values());
    }

    public void registerRenderer(Class<? extends Component> clazz, NvgLeguiComponentRenderer renderer) {
        rendererMap.put(clazz, renderer);
    }

    public void registerRenderer(Class<? extends Border> clazz, NvgLeguiBorderRenderer renderer) {
        borderRendererMap.put(clazz, renderer);
    }
}

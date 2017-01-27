package org.liquidengine.legui.render.nvg;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.render.LeguiBorderRenderer;
import org.liquidengine.legui.render.LeguiComponentRenderer;
import org.liquidengine.legui.render.LeguiRendererProvider;
import org.liquidengine.legui.render.nvg.border.NvgSimpleLineBorderRenderer;
import org.liquidengine.legui.render.nvg.component.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shcherbin Alexander on 9/20/2016.
 */
public class NvgRendererProvider extends LeguiRendererProvider {
    private Map<Class<? extends Component>, LeguiComponentRenderer> rendererMap       = new ConcurrentHashMap<>();
    private Map<Class<? extends Border>, LeguiBorderRenderer>       borderRendererMap = new ConcurrentHashMap<>();
    private NvgDefaultRenderer                                      renderer          = new NvgDefaultRenderer();
    private NvgLeguiBorderRenderer                                  borderRenderer    = new NvgSimpleLineBorderRenderer();

    public NvgRendererProvider() {
        initialize();
    }

    private void initialize() {

        // components
        registerRenderer(Label.class, new NvgLabelRenderer());
        registerRenderer(ImageView.class, new NvgImageRenderer());
        registerRenderer(Button.class, new NvgButtonRenderer());
        registerRenderer(CheckBox.class, new NvgCheckBoxRenderer());
        registerRenderer(ProgressBar.class, new NvgProgressBarRenderer());
        registerRenderer(RadioButton.class, new NvgRadioButtonRenderer());
        registerRenderer(Slider.class, new NvgSliderRenderer());
        registerRenderer(ScrollBar.class, new NvgScrollBarRenderer());
        registerRenderer(TextInput.class, new NvgTextInputRenderer());
        registerRenderer(TextArea.class, new NvgTextAreaRenderer());
        registerRenderer(ToggleButton.class, new NvgToggleButtonRenderer());
//
//        // borders
        registerRenderer(SimpleRectangleLineBorder.class, new NvgSimpleLineBorderRenderer());

    }

    @Override
    public LeguiComponentRenderer getRenderer(Component component) {
        return cycledSearchOfRenderer(component.getClass(), renderer);
    }


    private LeguiComponentRenderer cycledSearchOfRenderer(Class<? extends Component> componentClass, LeguiComponentRenderer defaultRenderer) {
        LeguiComponentRenderer renderer = null;
        Class                  cClass   = componentClass;
        while (renderer == null) {
            renderer = rendererMap.get(cClass);
            if (cClass.isAssignableFrom(Component.class)) break;
            cClass = cClass.getSuperclass();
        }
        if (renderer == null) renderer = defaultRenderer;
        return renderer;
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

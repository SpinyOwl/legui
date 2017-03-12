package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.system.renderer.BorderRenderer;
import org.liquidengine.legui.system.renderer.ComponentRenderer;
import org.liquidengine.legui.system.renderer.IconRenderer;
import org.liquidengine.legui.system.renderer.RendererProvider;
import org.liquidengine.legui.system.renderer.nvg.border.NvgDefaultBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.border.NvgSimpleLineBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.component.*;
import org.liquidengine.legui.system.renderer.nvg.icon.NvgDefaultIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.icon.NvgImageIconRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgRendererProvider extends RendererProvider {
    private Map<Class<? extends Component>, NvgComponentRenderer<? extends Component>> componentRendererMap     = new ConcurrentHashMap<>();
    private Map<Class<? extends Border>, NvgBorderRenderer<? extends Border>>          borderRendererMap        = new ConcurrentHashMap<>();
    private Map<Class<? extends Icon>, NvgIconRenderer<? extends Icon>>                iconRendererMap          = new ConcurrentHashMap<>();
    private NvgComponentRenderer                                                       defaultComponentRenderer = new NvgDefaultComponentRenderer();
    private NvgBorderRenderer                                                          defaultBorderRenderer    = new NvgDefaultBorderRenderer();
    private NvgIconRenderer                                                            defaultIconRenderer      = new NvgDefaultIconRenderer();

    public NvgRendererProvider() {

        // register component renderers
        componentRendererMap.put(Container.class, new NvgContainerRenderer());
        componentRendererMap.put(Button.class, new NvgButtonRenderer());
        componentRendererMap.put(ToggleButton.class, new NvgToggleButtonRenderer());
        componentRendererMap.put(ImageView.class, new NvgImageViewRenderer());
        componentRendererMap.put(CheckBox.class, new NvgCheckBoxRenderer());
        componentRendererMap.put(Label.class, new NvgLabelRenderer());
        componentRendererMap.put(ProgressBar.class, new NvgProgressBarRenderer());
        componentRendererMap.put(RadioButton.class, new NvgRadioButtonRenderer());
        componentRendererMap.put(ScrollBar.class, new NvgScrollBarRenderer());
        componentRendererMap.put(Slider.class, new NvgSliderRenderer());
        componentRendererMap.put(TextArea.class, new NvgTextAreaRenderer());
        componentRendererMap.put(TextInput.class, new NvgTextInputRenderer());
        componentRendererMap.put(Tooltip.class, new NvgTooltipRenderer());

        // register border renderers
        borderRendererMap.put(SimpleLineBorder.class, new NvgSimpleLineBorderRenderer());

        // register icon renderers
        iconRendererMap.put(ImageIcon.class, new NvgImageIconRenderer<>());
    }

    @Override
    public <C extends Component> ComponentRenderer<C> getComponentRenderer(Class<C> componentClass) {
        return this.<C, ComponentRenderer<C>>cycledSearchOfRenderer(componentClass, componentRendererMap, defaultComponentRenderer);
    }

    @Override
    public <B extends Border> BorderRenderer<B> getBorderRenderer(Class<B> borderClass) {
        return this.<B, BorderRenderer<B>>cycledSearchOfRenderer(borderClass, borderRendererMap, defaultBorderRenderer);
    }

    @Override
    public <C extends Icon> IconRenderer getIconRenderer(Class<C> iconClass) {
        return this.<C, IconRenderer<C>>cycledSearchOfRenderer(iconClass, iconRendererMap, defaultIconRenderer);
    }

    private <C, R> R cycledSearchOfRenderer(Class<C> componentClass, Map map, R defaultRenderer) {
        R     renderer = null;
        Class cClass   = componentClass;
        while (renderer == null) {
            renderer = ((Map<Class<C>, R>) map).get(cClass);
            if (cClass.isAssignableFrom(Component.class)) break;
            cClass = cClass.getSuperclass();
        }
        if (renderer == null) renderer = defaultRenderer;
        return renderer;
    }


    @Override
    public List<ComponentRenderer> getComponentRenderers() {
        return new ArrayList<>(componentRendererMap.values());
    }
}

package org.liquidengine.legui.system.renderer.nvg;

import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.system.renderer.*;
import org.liquidengine.legui.system.renderer.nvg.border.NvgDefaultLeguiBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.border.NvgSimpleLineLeguiBorderRenderer;
import org.liquidengine.legui.system.renderer.nvg.component.*;
import org.liquidengine.legui.system.renderer.nvg.icon.NvgCharLeguiIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.icon.NvgDefaultLeguiIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.icon.NvgImageLeguiIconRenderer;
import org.liquidengine.legui.system.renderer.nvg.image.NvgDefaultLeguiImageRenderer;
import org.liquidengine.legui.system.renderer.nvg.image.NvgLoadableLeguiImageRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Aliaksandr_Shcherbin on 1/26/2017.
 */
public class NvgLeguiRendererProvider extends LeguiRendererProvider {
    private Map<Class<? extends Component>, NvgLeguiComponentRenderer<? extends Component>> componentRendererMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Border>, NvgLeguiBorderRenderer<? extends Border>>          borderRendererMap    = new ConcurrentHashMap<>();
    private Map<Class<? extends Icon>, NvgLeguiIconRenderer<? extends Icon>>                iconRendererMap      = new ConcurrentHashMap<>();
    private Map<Class<? extends Image>, NvgLeguiImageRenderer<? extends Image>>             imageRendererMap     = new ConcurrentHashMap<>();

    private NvgLeguiComponentRenderer defaultComponentRenderer = new NvgDefaultLeguiComponentRenderer();
    private NvgLeguiBorderRenderer    defaultBorderRenderer    = new NvgDefaultLeguiBorderRenderer();
    private NvgLeguiIconRenderer      defaultIconRenderer      = new NvgDefaultLeguiIconRenderer();
    private NvgLeguiImageRenderer     defaultImageRenderer     = new NvgDefaultLeguiImageRenderer();

    private NvgLeguiRendererProvider() {

        // register component renderers
        componentRendererMap.put(Container.class, new NvgContainerRendererLegui());
        componentRendererMap.put(Button.class, new NvgButtonRendererLegui());
        componentRendererMap.put(ToggleButton.class, new NvgToggleButtonRendererLegui());
        componentRendererMap.put(ImageView.class, new NvgImageViewRendererLegui());
        componentRendererMap.put(CheckBox.class, new NvgCheckBoxRendererLegui());
        componentRendererMap.put(Label.class, new NvgLabelRendererLegui());
        componentRendererMap.put(ProgressBar.class, new NvgProgressBarRendererLegui());
        componentRendererMap.put(RadioButton.class, new NvgRadioButtonRendererLegui());
        componentRendererMap.put(ScrollBar.class, new NvgScrollBarRendererLegui());
        componentRendererMap.put(Slider.class, new NvgSliderRendererLegui());
        componentRendererMap.put(TextArea.class, new NvgTextAreaRendererLegui());
        componentRendererMap.put(TextInput.class, new NvgTextInputRendererLegui());
        componentRendererMap.put(PasswordInput.class, new NvgPasswordInputRendererLegui());
        componentRendererMap.put(Tooltip.class, new NvgTooltipRendererLegui());

        // register border renderers
        borderRendererMap.put(SimpleLineBorder.class, new NvgSimpleLineLeguiBorderRenderer());

        // register icon renderers
        iconRendererMap.put(ImageIcon.class, new NvgImageLeguiIconRenderer<>());
        iconRendererMap.put(CharIcon.class, new NvgCharLeguiIconRenderer<>());

        // register image renderers
        imageRendererMap.put(LoadableImage.class, new NvgLoadableLeguiImageRenderer<>());
    }

    public static NvgLeguiRendererProvider getInstance() {
        return NRPH.I;
    }

    @Override
    public <C extends Component> LeguiComponentRenderer<C> getComponentRenderer(Class<C> componentClass) {
        return this.<C, LeguiComponentRenderer<C>>cycledSearchOfRenderer(componentClass, componentRendererMap, defaultComponentRenderer);
    }

    @Override
    public <B extends Border> LeguiBorderRenderer<B> getBorderRenderer(Class<B> borderClass) {
        return this.<B, LeguiBorderRenderer<B>>cycledSearchOfRenderer(borderClass, borderRendererMap, defaultBorderRenderer);
    }

    @Override
    public <C extends Icon> LeguiIconRenderer getIconRenderer(Class<C> iconClass) {
        return this.<C, LeguiIconRenderer<C>>cycledSearchOfRenderer(iconClass, iconRendererMap, defaultIconRenderer);
    }

    @Override
    public <I extends Image> LeguiImageRenderer getImageRenderer(Class<I> imageClass) {
        return this.<I, LeguiImageRenderer<I>>cycledSearchOfRenderer(imageClass, imageRendererMap, defaultImageRenderer);
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

    public <I extends Component, R extends NvgLeguiComponentRenderer<I>> void putComponentRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) return;
        componentRendererMap.put(imageClass, renderer);
    }

    public <I extends Border, R extends NvgLeguiBorderRenderer<I>> void putBorderRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) return;
        borderRendererMap.put(imageClass, renderer);
    }


    public <I extends Icon, R extends NvgLeguiIconRenderer<I>> void putIconRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) return;
        iconRendererMap.put(imageClass, renderer);
    }


    public <I extends Image, R extends NvgLeguiImageRenderer<I>> void putImageRenderer(Class<I> imageClass, R renderer) {
        if (imageClass == null || renderer == null) return;
        imageRendererMap.put(imageClass, renderer);
    }

    @Override
    public List<LeguiComponentRenderer> getComponentRenderers() {
        return new ArrayList<>(componentRendererMap.values());
    }

    private static final class NRPH {
        private static final NvgLeguiRendererProvider I = new NvgLeguiRendererProvider();
    }
}

package com.spinyowl.legui.system.renderer.nvg;

import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.CheckBox;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.ImageView;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.component.PasswordInput;
import com.spinyowl.legui.component.ProgressBar;
import com.spinyowl.legui.component.RadioButton;
import com.spinyowl.legui.component.ScrollBar;
import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.component.TextAreaField;
import com.spinyowl.legui.component.TextInput;
import com.spinyowl.legui.component.ToggleButton;
import com.spinyowl.legui.component.Tooltip;
import com.spinyowl.legui.icon.CharIcon;
import com.spinyowl.legui.icon.Icon;
import com.spinyowl.legui.icon.ImageIcon;
import com.spinyowl.legui.image.BufferedImageRGBA;
import com.spinyowl.legui.image.FBOImage;
import com.spinyowl.legui.image.Image;
import com.spinyowl.legui.image.StbBackedLoadableImage;
import com.spinyowl.legui.style.Border;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.system.renderer.BorderRenderer;
import com.spinyowl.legui.system.renderer.ComponentRenderer;
import com.spinyowl.legui.system.renderer.IconRenderer;
import com.spinyowl.legui.system.renderer.ImageRenderer;
import com.spinyowl.legui.system.renderer.RendererProvider;
import com.spinyowl.legui.system.renderer.nvg.border.NvgDefaultBorderRenderer;
import com.spinyowl.legui.system.renderer.nvg.border.NvgSimpleLineBorderRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgButtonRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgCheckBoxRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgDefaultComponentRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgImageViewRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgLabelRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgPasswordInputRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgProgressBarRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgRadioButtonRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgScrollBarRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgSliderRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgTextAreaFieldRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgTextInputRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgToggleButtonRenderer;
import com.spinyowl.legui.system.renderer.nvg.component.NvgTooltipRenderer;
import com.spinyowl.legui.system.renderer.nvg.icon.NvgCharIconRenderer;
import com.spinyowl.legui.system.renderer.nvg.icon.NvgDefaultIconRenderer;
import com.spinyowl.legui.system.renderer.nvg.icon.NvgImageIconRenderer;
import com.spinyowl.legui.system.renderer.nvg.image.NvgBufferedImageRGBARenderer;
import com.spinyowl.legui.system.renderer.nvg.image.NvgDefaultImageRenderer;
import com.spinyowl.legui.system.renderer.nvg.image.NvgFBOImageRenderer;
import com.spinyowl.legui.system.renderer.nvg.image.NvgStbBackedLoadableImageRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class NvgRendererProvider implements RendererProvider {

  private final Map<Class<? extends Component>, ComponentRenderer<? extends Component>> componentRendererMap = new ConcurrentHashMap<>();
  private final Map<Class<? extends Border>, BorderRenderer<? extends Border>> borderRendererMap = new ConcurrentHashMap<>();
  private final Map<Class<? extends Icon>, IconRenderer<? extends Icon>> iconRendererMap = new ConcurrentHashMap<>();
  private final Map<Class<? extends Image>, ImageRenderer<? extends Image>> imageRendererMap = new ConcurrentHashMap<>();

  private final NvgComponentRenderer defaultComponentRenderer = new NvgDefaultComponentRenderer();
  private final NvgBorderRenderer defaultBorderRenderer = new NvgDefaultBorderRenderer();
  private final NvgIconRenderer defaultIconRenderer = new NvgDefaultIconRenderer();
  private final NvgImageRenderer defaultImageRenderer = new NvgDefaultImageRenderer();

  private NvgRendererProvider() {

    // register component renderers
    componentRendererMap.put(Button.class, new NvgButtonRenderer());
    componentRendererMap.put(ToggleButton.class, new NvgToggleButtonRenderer());
    componentRendererMap.put(ImageView.class, new NvgImageViewRenderer());
    componentRendererMap.put(CheckBox.class, new NvgCheckBoxRenderer());
    componentRendererMap.put(Label.class, new NvgLabelRenderer());
    componentRendererMap.put(ProgressBar.class, new NvgProgressBarRenderer());
    componentRendererMap.put(RadioButton.class, new NvgRadioButtonRenderer());
    componentRendererMap.put(ScrollBar.class, new NvgScrollBarRenderer());
    componentRendererMap.put(Slider.class, new NvgSliderRenderer());
    componentRendererMap.put(TextAreaField.class, new NvgTextAreaFieldRenderer());
    componentRendererMap.put(TextInput.class, new NvgTextInputRenderer());
    componentRendererMap.put(PasswordInput.class, new NvgPasswordInputRenderer());
    componentRendererMap.put(Tooltip.class, new NvgTooltipRenderer());

    // register border renderers
    borderRendererMap.put(SimpleLineBorder.class, new NvgSimpleLineBorderRenderer());

    // register icon renderers
    iconRendererMap.put(ImageIcon.class, new NvgImageIconRenderer<>());
    iconRendererMap.put(CharIcon.class, new NvgCharIconRenderer<>());

    // register image renderers
    imageRendererMap.put(StbBackedLoadableImage.class, new NvgStbBackedLoadableImageRenderer());
    imageRendererMap.put(FBOImage.class, new NvgFBOImageRenderer());
    imageRendererMap.put(BufferedImageRGBA.class, new NvgBufferedImageRGBARenderer());
  }

  public static NvgRendererProvider getInstance() {
    return NRPH.I;
  }

  @Override
  public <C extends Component> ComponentRenderer<C> getComponentRenderer(Class<C> componentClass) {
    return this.<C, ComponentRenderer<C>>cycledSearchOfRenderer(componentClass,
        componentRendererMap, defaultComponentRenderer);
  }

  @Override
  public <B extends Border> BorderRenderer<B> getBorderRenderer(Class<B> borderClass) {
    return this.<B, BorderRenderer<B>>cycledSearchOfRenderer(borderClass, borderRendererMap,
        defaultBorderRenderer);
  }

  @Override
  public <C extends Icon> IconRenderer getIconRenderer(Class<C> iconClass) {
    return this.<C, IconRenderer<C>>cycledSearchOfRenderer(iconClass, iconRendererMap,
        defaultIconRenderer);
  }

  @Override
  public <I extends Image> ImageRenderer getImageRenderer(Class<I> imageClass) {
    return this.<I, ImageRenderer<I>>cycledSearchOfRenderer(imageClass, imageRendererMap,
        defaultImageRenderer);
  }

  private <C, R> R cycledSearchOfRenderer(Class<C> componentClass, Map map, R defaultRenderer) {
    R renderer = null;
    Class cClass = componentClass;
    while (renderer == null) {
      renderer = (R) map.get(cClass);
      if (cClass.isAssignableFrom(Component.class)) {
        break;
      }
      cClass = cClass.getSuperclass();
    }
    if (renderer == null) {
      renderer = defaultRenderer;
    }
    return renderer;
  }

  public <I extends Component, R extends NvgComponentRenderer<I>> void putComponentRenderer(
      Class<I> imageClass, R renderer) {
    addComponentRenderer(imageClass, renderer);
  }

  public <I extends Component, R extends ComponentRenderer<I>> void addComponentRenderer(
      Class<I> imageClass, R renderer) {
    if (imageClass == null || renderer == null) {
      return;
    }
    componentRendererMap.put(imageClass, renderer);
  }

  public <I extends Border, R extends BorderRenderer<I>> void addBorderRenderer(Class<I> imageClass,
      R renderer) {
    if (imageClass == null || renderer == null) {
      return;
    }
    borderRendererMap.put(imageClass, renderer);
  }

  public <I extends Icon, R extends IconRenderer<I>> void addIconRenderer(Class<I> imageClass,
      R renderer) {
    if (imageClass == null || renderer == null) {
      return;
    }
    iconRendererMap.put(imageClass, renderer);
  }

  public <I extends Image, R extends ImageRenderer<I>> void addImageRenderer(Class<I> imageClass,
      R renderer) {
    if (imageClass == null || renderer == null) {
      return;
    }
    imageRendererMap.put(imageClass, renderer);
  }

  @Override
  public List<ComponentRenderer> getComponentRenderers() {
    return new ArrayList<>(componentRendererMap.values());
  }

  private static final class NRPH {

    private static final NvgRendererProvider I = new NvgRendererProvider();
  }
}

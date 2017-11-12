package org.liquidengine.legui.marshal.json.gsonimpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.CheckBox;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Layer;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.ProgressBar;
import org.liquidengine.legui.component.RadioButton;
import org.liquidengine.legui.component.ScrollBar;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.Slider;
import org.liquidengine.legui.component.TextArea;
import org.liquidengine.legui.component.TextInput;
import org.liquidengine.legui.component.ToggleButton;
import org.liquidengine.legui.component.Tooltip;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptionTemplate;
import org.liquidengine.legui.icon.CharIcon;
import org.liquidengine.legui.icon.Icon;
import org.liquidengine.legui.icon.ImageIcon;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.LoadableImage;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.marshal.json.JsonMarshalRegistry;
import org.liquidengine.legui.marshal.json.JsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.border.GsonBorderMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.border.GsonSimpleLineBorderMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonButtonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonCheckBoxMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonComponentMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonFrameMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonImageViewMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonLabelMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonLayerContainerMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonLayerMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonPanelMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonProgressBarMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonRadioButtonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonScrollBarMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonScrollablePanelMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonSelectBoxMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonSliderMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonTextAreaMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonTextInputMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonToggleButtonMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonTooltipMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.GsonWidgetMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.component.optional.GsonTextStateMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.icon.GsonCharIconMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.icon.GsonIconMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.icon.GsonLoadableImageIconMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.image.GsonBufferedImageMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.image.GsonLoadableImageMarshaller;
import org.liquidengine.legui.marshal.json.gsonimpl.intersector.GsonIntersectorMarshaller;

/**
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public class GsonMarshalRegistry implements JsonMarshalRegistry {

    private Map<Class<?>, JsonMarshaller<?>> marshallerMap = new ConcurrentHashMap<>();
    private BidiMap<String, Class<?>> typeMap = new DualHashBidiMap<>();
    private Lock lock = new ReentrantLock(false);

    private GsonMarshalRegistry() {
    }

    public static GsonMarshalRegistry getRegistry() {
        return JSRIH.I;
    }

    public Class<?> getClassByShortType(String shortName) {
        return typeMap.get(shortName);
    }

    public String getShortTypeByClass(Class<?> clazz) {
        return typeMap.getKey(clazz);
    }

    /**
     * Register marshaller for specified class.
     *
     * @param typeName short type name, can be null, in that case {@link GsonMarshalRegistry#getShortTypeByClass(Class)} will return null.
     * @param tClass class.
     * @param marshaller marshaller.
     * @param <T> type parameter to prevent error in marshal registry..
     */
    public <T> void registerMarshaller(String typeName, Class<T> tClass, JsonMarshaller<T> marshaller) {
        lock.lock();
        try {
            if (typeName != null) {
                if (typeMap.containsKey(typeName)) {
                    throw new LeguiException(LeguiExceptionTemplate.GSON_REGISTRY_TYPE_EXIST.message(typeName));
                }
                typeMap.put(typeName, tClass);
            }
            marshallerMap.put(tClass, marshaller);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns marshaller for specified class.
     *
     * @param tClass class.
     * @param <T> type of marshaled/demarshaled class.
     *
     * @return json marshaller for specified class.
     */
    public <T> AbstractGsonMarshaller<T> getMarshaller(Class<T> tClass) {
        return treeGetMarshaller(tClass);
    }

    /**
     * Returns marshaller for specified classname.
     *
     * @param classname full classname.
     *
     * @return json marshaller for specified classname.
     */
    public AbstractGsonMarshaller getMarshaller(String classname) {
        try {
            Class tclass = Class.forName(classname);
            return getMarshaller(tclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns marshaller for specified type.
     *
     * @param typeName type name.
     *
     * @return json marshaller for specified type.
     */
    public AbstractGsonMarshaller getMarshallerByShortType(String typeName) {
        Class<?> tclass = typeMap.get(typeName);
        if (tclass != null) {
            return getMarshaller(tclass);
        } else {
            return null;
        }
    }

    /**
     * Returns marshaller for specified class.
     *
     * @param tClass class.
     *
     * @return json marshaller for specified class.
     */
    private <T> AbstractGsonMarshaller<T> treeGetMarshaller(Class<T> tClass) {
        AbstractGsonMarshaller<T> marshaller = (AbstractGsonMarshaller<T>) marshallerMap.get(tClass);
        Class cClass = tClass.getSuperclass();
        while (marshaller == null) {
            marshaller = (AbstractGsonMarshaller<T>) marshallerMap.get(cClass);
            if (cClass.isAssignableFrom(Object.class)) {
                break;
            }
            cClass = cClass.getSuperclass();
        }
        return marshaller;
    }

    /**
     * Singleton implementation "On demand holder".
     */
    private static class JSRIH {

        private static final GsonMarshalRegistry I = new GsonMarshalRegistry();

        static {
            // icons
            // TODO: Add CharIcon and ImageIcon marshaller.
            I.registerMarshaller("Icon", Icon.class, new GsonIconMarshaller<>());
            I.registerMarshaller("ImageIcon", ImageIcon.class, new GsonLoadableImageIconMarshaller<>());
            I.registerMarshaller("CharIcon", CharIcon.class, new GsonCharIconMarshaller<>());

            //image
            I.registerMarshaller("LoadableImage", LoadableImage.class, new GsonLoadableImageMarshaller<>());
            I.registerMarshaller("BufferedImage", BufferedImage.class, new GsonBufferedImageMarshaller<>());

            // textstate
            I.registerMarshaller("TextState", TextState.class, new GsonTextStateMarshaller<>());

            // borders
            I.registerMarshaller("Border", Border.class, new GsonBorderMarshaller<>());
            I.registerMarshaller("SimpleLineBorder", SimpleLineBorder.class, new GsonSimpleLineBorderMarshaller());

            // intersector
            I.registerMarshaller("Intersector", Intersector.class, new GsonIntersectorMarshaller<>());

            // components
            I.registerMarshaller("Button", Button.class, new GsonButtonMarshaller<>());
            I.registerMarshaller("CheckBox", CheckBox.class, new GsonCheckBoxMarshaller<>());
            I.registerMarshaller("Component", Component.class, new GsonComponentMarshaller<>());
            I.registerMarshaller("ImageView", ImageView.class, new GsonImageViewMarshaller<>());
            I.registerMarshaller("Label", Label.class, new GsonLabelMarshaller<>());
            I.registerMarshaller("Panel", Panel.class, new GsonPanelMarshaller<>());
            I.registerMarshaller("ProgressBar", ProgressBar.class, new GsonProgressBarMarshaller<>());
            I.registerMarshaller("RadioButton", RadioButton.class, new GsonRadioButtonMarshaller<>());
            I.registerMarshaller("ScrollablePanel", ScrollablePanel.class, new GsonScrollablePanelMarshaller<>());
            I.registerMarshaller("ScrollBar", ScrollBar.class, new GsonScrollBarMarshaller<>());
            I.registerMarshaller("SelectBox", SelectBox.class, new GsonSelectBoxMarshaller<>());
            I.registerMarshaller("Slider", Slider.class, new GsonSliderMarshaller<>());
            I.registerMarshaller("TextArea", TextArea.class, new GsonTextAreaMarshaller<>());
            I.registerMarshaller("TextInput", TextInput.class, new GsonTextInputMarshaller<>());
            I.registerMarshaller("Widget", Widget.class, new GsonWidgetMarshaller<>());
            I.registerMarshaller("LayerContainer", LayerContainer.class, new GsonLayerContainerMarshaller<>());
            I.registerMarshaller("Layer", Layer.class, new GsonLayerMarshaller<>());
            I.registerMarshaller("Frame", Frame.class, new GsonFrameMarshaller<>());
            I.registerMarshaller("Tooltip", Tooltip.class, new GsonTooltipMarshaller<>());
            I.registerMarshaller("ToggleButton", ToggleButton.class, new GsonToggleButtonMarshaller<>());

        }
    }
}

package org.liquidengine.legui.marshal.json.gsonImpl;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.liquidengine.legui.border.Border;
import org.liquidengine.legui.border.SimpleLineBorder;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.intersection.Intersector;
import org.liquidengine.legui.marshal.json.JsonMarshalRegistry;
import org.liquidengine.legui.marshal.json.JsonMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.border.GsonBorderMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.border.GsonSimpleLineBorderMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.component.*;
import org.liquidengine.legui.marshal.json.gsonImpl.component.optional.GsonTextStateMarshaller;
import org.liquidengine.legui.marshal.json.gsonImpl.intersector.GsonIntersectorMarshaller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Aliaksandr_Shcherbin on 2/24/2017.
 */
public class GsonMarshalRegistry implements JsonMarshalRegistry {
    private Map<Class<?>, JsonMarshaller<?>> marshallerMap = new ConcurrentHashMap<>();
    private BidiMap<String, Class<?>>        typeMap       = new DualHashBidiMap<>();
    private Lock                             lock          = new ReentrantLock(false);

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
     * Register marshaller for specified class
     *
     * @param typeName   short type name, can be null, in that case {@link GsonMarshalRegistry#getShortTypeByClass(Class)} will return null
     * @param tClass     class
     * @param marshaller marshaller
     * @param <T>        type parameter to prevent error in marshal registry.
     */
    public <T> void registerMarshaller(String typeName, Class<T> tClass, JsonMarshaller<T> marshaller) {
        lock.lock();
        try {
            if (typeName != null) {
                if (typeMap.containsKey(typeName)) {
                    throw new LeguiException(LeguiExceptions.GSON_REGISTRY_TYPE_EXIST.message(typeName));
                }
                typeMap.put(typeName, tClass);
            }
            marshallerMap.put(tClass, marshaller);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns marshaller for specified class
     *
     * @param tClass class
     * @param <T>    type of marshalled/demarshalled class
     * @return json marshaller for specified class
     */
    public <T> AbstractGsonMarshaller<T> getMarshaller(Class<T> tClass) {
        return treeGetMarshaller(tClass);
    }

    /**
     * Returns marshaller for specified classname
     *
     * @param classname full classname
     * @return json marshaller for specified classname
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
     * Returns marshaller for specified type
     *
     * @param typeName type name
     * @return json marshaller for specified type
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
     * Returns marshaller for specified class
     *
     * @param tClass class
     * @return json marshaller for specified class
     */
    private <T> AbstractGsonMarshaller<T> treeGetMarshaller(Class<T> tClass) {
        AbstractGsonMarshaller<T> marshaller = (AbstractGsonMarshaller<T>) marshallerMap.get(tClass);
        Class                     cClass     = tClass.getSuperclass();
        while (marshaller == null) {
            marshaller = (AbstractGsonMarshaller<T>) marshallerMap.get(cClass);
            if (cClass.isAssignableFrom(Object.class)) break;
            cClass = cClass.getSuperclass();
        }
        return marshaller;
    }

    /**
     * Singleton implementation "On demand holder"
     */
    private static class JSRIH {
        private static final GsonMarshalRegistry I = new GsonMarshalRegistry();

        static {
            I.registerMarshaller("TextState", TextState.class, new GsonTextStateMarshaller<>());

            I.registerMarshaller("Border", Border.class, new GsonBorderMarshaller<>());
            I.registerMarshaller("SimpleLineBorder", SimpleLineBorder.class, new GsonSimpleLineBorderMarshaller());

            I.registerMarshaller("Intersector", Intersector.class, new GsonIntersectorMarshaller<>());

            I.registerMarshaller("Button", Button.class, new GsonButtonMarshaller<>());
            I.registerMarshaller("CheckBox", CheckBox.class, new GsonCheckBoxMarshaller<>());
            I.registerMarshaller("Container", Container.class, new GsonContainerMarshaller<>());
            I.registerMarshaller("Component", Component.class, new GsonComponentMarshaller<>());
            I.registerMarshaller("Controller", Controller.class, new GsonControllerMarshaller<>());
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

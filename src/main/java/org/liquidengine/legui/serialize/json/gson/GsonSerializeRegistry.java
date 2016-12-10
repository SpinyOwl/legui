package org.liquidengine.legui.serialize.json.gson;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.border.Border;
import org.liquidengine.legui.component.border.SimpleRectangleLineBorder;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.exception.LeguiException;
import org.liquidengine.legui.exception.LeguiExceptions;
import org.liquidengine.legui.serialize.json.gson.component.*;
import org.liquidengine.legui.serialize.json.gson.component.optional.GsonBorderSerializer;
import org.liquidengine.legui.serialize.json.gson.component.optional.GsonSimpleRectangleLineBorderSerializer;
import org.liquidengine.legui.serialize.json.gson.component.optional.GsonTextStateSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonSerializeRegistry {
    private Map<Class<?>, AbstractGsonSerializer<?>> serializerMap = new ConcurrentHashMap<>();
    private BidiMap<String, Class<?>> typeMap = new DualHashBidiMap<>();
    private Lock lock = new ReentrantLock(false);

    private GsonSerializeRegistry() {
    }

    public static GsonSerializeRegistry getRegistry() {
        return JSRIH.I;
    }

    public Class<?> getClassByShortType(String shortName) {
        return typeMap.get(shortName);
    }

    public String getShortTypeByClass(Class<?> clazz) {
        return typeMap.getKey(clazz);
    }

    /**
     * Register serializer for specified class
     *
     * @param typeName   short type name, can be null, in that case {@link GsonSerializeRegistry#getShortTypeByClass(Class)} will return null
     * @param tClass     class
     * @param serializer serializer
     * @param <T>        type parameter to prevent error in serialize registry.
     */
    public <T> void registerSerializer(String typeName, Class<T> tClass, AbstractGsonSerializer<T> serializer) {
        lock.lock();
        try {
            if (typeName != null) {
                if (typeMap.containsKey(typeName)) {
                    throw new LeguiException(LeguiExceptions.GSON_REGISTRY_TYPE_EXIST.message(typeName));
                }
                typeMap.put(typeName, tClass);
            }
            serializerMap.put(tClass, serializer);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns serializer for specified class
     *
     * @param tClass class
     * @return json serializer for specified class
     */
    public <T> AbstractGsonSerializer<T> getSerializer(Class<T> tClass) {
        return treeGetSerializer(tClass);
    }

    /**
     * Returns serializer for specified classname
     *
     * @param classname full classname
     * @return json serializer for specified classname
     */
    public AbstractGsonSerializer getSerializer(String classname) {
        try {
            Class tclass = Class.forName(classname);
            return getSerializer(tclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns serializer for specified type
     *
     * @param typeName type name
     * @return json serializer for specified type
     */
    public AbstractGsonSerializer getSerializerByShortType(String typeName) {
        Class<?> tclass = typeMap.get(typeName);
        if (tclass != null) {
            return getSerializer(tclass);
        } else {
            return null;
        }
    }

    /**
     * Returns serializer for specified class
     *
     * @param tClass class
     * @return json serializer for specified class
     */
    private <T> AbstractGsonSerializer<T> treeGetSerializer(Class<T> tClass) {
        AbstractGsonSerializer<T> serializer = (AbstractGsonSerializer<T>) serializerMap.get(tClass);
        Class cClass = tClass.getSuperclass();
        while (serializer == null) {
            serializer = (AbstractGsonSerializer<T>) serializerMap.get(cClass);
            if (cClass.isAssignableFrom(Object.class)) break;
            cClass = cClass.getSuperclass();
        }
        return serializer;
    }

    /**
     * Singleton implementation "On demand holder"
     */
    private static class JSRIH {
        private static final GsonSerializeRegistry I = new GsonSerializeRegistry();

        static {
            I.registerSerializer("TextState", TextState.class, new GsonTextStateSerializer());

            I.registerSerializer("Border", Border.class, new GsonBorderSerializer());
            I.registerSerializer("SimpleRectangleLineBorder", SimpleRectangleLineBorder.class, new GsonSimpleRectangleLineBorderSerializer());

            I.registerSerializer("Button", Button.class, new GsonButtonSerializer());
            I.registerSerializer("CheckBox", CheckBox.class, new GsonCheckboxSerializer());
            I.registerSerializer("ComponentContainer", ComponentContainer.class, new GsonComponentContainerSerializer<>());
            I.registerSerializer("Component", Component.class, new GsonComponentSerializer<>());
            I.registerSerializer("Image", Image.class, new GsonImageSerializer());
            I.registerSerializer("Label", Label.class, new GsonLabelSerializer());
            I.registerSerializer("Panel", Panel.class, new GsonPanelSerializer());
            I.registerSerializer("ProgressBar", ProgressBar.class, new GsonProgressBarSerializer());
            I.registerSerializer("RadioButton", RadioButton.class, new GsonRadioButtonSerializer());
            I.registerSerializer("ScrollablePanel", ScrollablePanel.class, new GsonScrollablePanelSerializer());
            I.registerSerializer("ScrollBar", ScrollBar.class, new GsonScrollBarSerializer());
            I.registerSerializer("SelectBox", SelectBox.class, new GsonSelectBoxSerializer());
            I.registerSerializer("Slider", Slider.class, new GsonSliderSerializer());
            I.registerSerializer("TextArea", TextArea.class, new GsonTextAreaSerializer());
            I.registerSerializer("TextInput", TextInput.class, new GsonTextInputSerializer());
            I.registerSerializer("Widget", Widget.class, new GsonWidgetSerializer());
        }
    }

}

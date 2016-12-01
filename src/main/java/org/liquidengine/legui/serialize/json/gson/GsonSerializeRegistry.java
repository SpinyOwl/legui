package org.liquidengine.legui.serialize.json.gson;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.serialize.json.gson.component.*;
import org.liquidengine.legui.serialize.json.gson.component.optional.GsonTextStateSerializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alexander on 26.11.2016.
 */
public final class GsonSerializeRegistry {
    private Map<Class<?>, AbstractGsonSerializer<?>> serializerMap = new ConcurrentHashMap<>();

    private GsonSerializeRegistry() {
    }

    public static GsonSerializeRegistry getRegistry() {
        return JSRIH.I;
    }

    /**
     * Register serializer for specified class
     *
     * @param tClass     class
     * @param serializer serializer
     * @param <T>        type parameter to prevent error in serialize registry.
     */
    public <T> void registerSerializer(Class<T> tClass, AbstractGsonSerializer<T> serializer) {
        serializerMap.put(tClass, serializer);
    }

    /**
     * Returns serializer for specified class
     *
     * @param tClass class
     * @return json serializer for specified class
     */
    public <T> AbstractGsonSerializer<T> getSerializer(Class<T> tClass) {
        return (AbstractGsonSerializer<T>) serializerMap.get(tClass);
    }

    /**
     * Returns serializer for specified type
     *
     * @param type full classname
     * @return json serializer for specified type
     */
    public AbstractGsonSerializer getSerializer(String type) {
        try {
            Class tclass = Class.forName(type);
            return getSerializer(tclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Singleton implementation "On demand holder"
     */
    private static class JSRIH {
        private static final GsonSerializeRegistry I = new GsonSerializeRegistry();

        static {
            I.registerSerializer(TextState.class, new GsonTextStateSerializer());

            I.registerSerializer(Button.class, new GsonButtonSerializer());
            I.registerSerializer(CheckBox.class, new GsonCheckboxSerializer());
            I.registerSerializer(Component.class, new GsonComponentSerializer());
            I.registerSerializer(Image.class, new GsonImageSerializer());
            I.registerSerializer(Label.class, new GsonLabelSerializer());
            I.registerSerializer(ProgressBar.class, new GsonProgressBarSerializer());
            I.registerSerializer(RadioButton.class, new GsonRadioButtonSerializer());
            I.registerSerializer(ScrollBar.class, new GsonScrollBarSerializer());
            I.registerSerializer(SelectBox.class, new GsonSelectBoxSerializer());
            I.registerSerializer(Slider.class, new GsonSliderSerializer());
            I.registerSerializer(TextArea.class, new GsonTextAreaSerializer());

        }
    }

}

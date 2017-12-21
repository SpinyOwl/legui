package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.binding.model.BindingUtilities.getFieldValue;
import static org.liquidengine.legui.marshal.json.JsonMarshalProperties.CLASS_PROPERTY;
import static org.liquidengine.legui.marshal.json.JsonMarshalProperties.TYPE_PROPERTY;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;
import org.liquidengine.legui.binding.BindingRegistry;
import org.liquidengine.legui.binding.accessor.AbstractFieldAccessor;
import org.liquidengine.legui.binding.model.AbstractClassBinding;
import org.liquidengine.legui.binding.model.Binding;

/**
 * Json serializer based on class binding.
 *
 * @author Aliaksandr_Shcherbin.
 */
public class BindingBasedJsonSerializer implements JsonSerializer {

    /**
     * Class binding to use.
     */
    private AbstractClassBinding classBinding;

    /**
     * Serializer Constructor.
     *
     * @param classBinding class binding to use.
     */
    public BindingBasedJsonSerializer(AbstractClassBinding classBinding) {
        this.classBinding = classBinding;
    }

    /**
     * Gson invokes this call-back method during serialization when it encounters a field of the
     * specified type.
     * <p>
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonSerializationContext#serialize(Object, Type)} method to create JsonElements for any
     * non-trivial field of the {@code src} object. However, you should never invoke it on the
     * {@code src} object itself since that will cause an infinite loop (Gson will call your
     * call-back method again).</p>
     *
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context con
     *
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        AbstractClassBinding classBinding = this.classBinding;
        AbstractClassBinding bForSrc = BindingRegistry.getInstance().getBinding(src.getClass());
        if (bForSrc != null) {
            classBinding = bForSrc;
        }

        return marshalToJson(src, classBinding, context);
    }

    /**
     * Used to marshal src to json element.
     *
     * @param src source object to marshal.
     * @param classBinding class binding to use.
     * @param context context.
     * @param <O> type.
     *
     * @return json element created from source object.
     */
    private <O> JsonElement marshalToJson(O src, AbstractClassBinding classBinding, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        }
        if (classBinding == null) {
            return context.serialize(src);
        }

        JsonObject object = new JsonObject();
        if (src.getClass().equals(classBinding.getBindingForType())) {
            object.addProperty(TYPE_PROPERTY, classBinding.getToName());
        } else {
            object.addProperty(CLASS_PROPERTY, src.getClass().getName());
        }

        List<Binding> bindingList = classBinding.getBindingList();
        for (Binding binding : bindingList) {
            String javaFieldName = binding.getJavaFieldName();
            String bindingFieldName = binding.getBindingFieldName();
            if (bindingFieldName == null) {
                bindingFieldName = javaFieldName;
            }

            Object fieldValue;
            AbstractFieldAccessor fieldAccessor = binding.getFieldAccessor();
            if (fieldAccessor != null) {
                fieldValue = fieldAccessor.getFieldValue(src);
            } else {
                fieldValue = getFieldValue(src, javaFieldName);
            }

            if (fieldValue == null) {
                continue;
            }

            JsonElement value = null;
            if (binding.getLinkedClassBinding() != null) {
                value = marshalToJson(fieldValue, binding.getLinkedClassBinding(), context);
            } else if (binding.getClassConverter() != null) {
                value = new JsonPrimitive(binding.getClassConverter().convertFromJava(fieldValue));
            } else {
                AbstractClassBinding<?> fieldBinding = BindingRegistry.getInstance().getBinding(fieldValue.getClass());
                if (fieldBinding != null) {
                    value = marshalToJson(fieldValue, fieldBinding, context);
                } else {
                    value = context.serialize(fieldValue);
                    if (value.isJsonObject()) {
                        JsonObject asJsonObject = value.getAsJsonObject();
                        if (!asJsonObject.has(TYPE_PROPERTY) && !asJsonObject.has(CLASS_PROPERTY)) {
                            JsonObject nv = new JsonObject();
                            nv.addProperty(CLASS_PROPERTY, fieldValue.getClass().getName());
                            for (Entry<String, JsonElement> entry : asJsonObject.entrySet()) {
                                nv.add(entry.getKey(), entry.getValue());
                            }
                            value = nv;
                        }
                    }
                }
            }
            object.add(bindingFieldName, value);
        }
        return object;
    }
}

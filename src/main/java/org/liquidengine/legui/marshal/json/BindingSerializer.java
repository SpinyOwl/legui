package org.liquidengine.legui.marshal.json;

import static org.liquidengine.legui.binding.model.BindingUtilities.getFieldValue;
import static org.liquidengine.legui.marshal.json.BindingProperties.CLASS_PROPERTY;
import static org.liquidengine.legui.marshal.json.BindingProperties.TYPE_PROPERTY;

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
 * @author Aliaksandr_Shcherbin.
 */
public class BindingSerializer implements JsonSerializer {

    private AbstractClassBinding classBinding;

    public BindingSerializer(AbstractClassBinding classBinding) {
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
     * @param context
     *
     * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        return marshalToJson(src, classBinding, context);
    }

    private <O> JsonElement marshalToJson(O src, AbstractClassBinding classBinding, JsonSerializationContext context) {
        if (src == null || classBinding == null) {
            return JsonNull.INSTANCE;
        }

        JsonObject object = new JsonObject();
        if (src.getClass().equals(classBinding.getBindingForType())) {
            object.addProperty(TYPE_PROPERTY.value(), classBinding.getToName());
        } else {
            object.addProperty(CLASS_PROPERTY.value(), src.getClass().getName());
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
                        if (!asJsonObject.has(TYPE_PROPERTY.value()) && !asJsonObject.has(CLASS_PROPERTY.value())) {
                            JsonObject nv = new JsonObject();
                            nv.addProperty(CLASS_PROPERTY.value(), fieldValue.getClass().getName());
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

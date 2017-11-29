package org.liquidengine.legui.marshal.j;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.liquidengine.legui.binding.Binding;
import org.liquidengine.legui.binding.Binding.Bind;
import org.liquidengine.legui.binding.BindingRegistry;
import org.reflections.ReflectionUtils;

/**
 * @author Aliaksandr_Shcherbin.
 */
public final class JsonMarshaller {

    private JsonMarshaller() {
    }

    public static String marshal(Object object) {
        return new Gson().toJson(marshalToJson(object));
    }

    public static JsonElement marshalToJson(Object object) {
        JsonElement json;
        Binding binding = BindingRegistry.getInstance().getBinding(object.getClass());
        if (binding != null) {
            json = marshalToJson(object, binding);
        } else {
            json = new Gson().toJsonTree(object);
        }
        return json;
    }

    public static String marshal(Object object, Binding binding) {
        Gson gson = new Gson();
        JsonElement e = marshalToJson(object, binding);
        return gson.toJson(e);
    }

    public static JsonElement marshalToJson(Object object, Binding binding) {
        JsonObject json = new JsonObject();
        Map<String, Bind> bindings = binding.getBindings();
        for (Entry<String, Bind> b : bindings.entrySet()) {
            Bind bind = b.getValue();
            String bindingField = bind.getBindingField();

            String fieldName = bind.getJavaField();
            Object field = getFieldValue(object, fieldName);
            if (bind.getLinkedBinding() != null) {
                json.add(bindingField, marshalToJson(field, bind.getLinkedBinding()));
            } else {
                json.add(bindingField, marshalToJson(field));
            }
        }
        return json;
    }

    private static Object getFieldValue(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();

        Object field = getFielFromObjectByInClass(object, fieldName, objectClass);

        // search in inherited
        Class<?> superclass;
        while (field == null && (superclass = objectClass.getSuperclass()) != null) {
            field = getFielFromObjectByInClass(object, fieldName, superclass);
        }

        return field;
    }

    private static Object getFielFromObjectByInClass(Object object, String fieldName, Class<?> objectClass) {
        Object field = null;
        // search getter or field in this object (not searchig in inherited fields and methods)
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Set<Method> getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        if (!getters.isEmpty()) {
            Method getter = getters.iterator().next();
            try {
                field = getter.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (field == null) {
            Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
            if (!fields.isEmpty()) {
                Field next = fields.iterator().next();
                try {
                    field = next.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return field;
    }
}

package org.liquidengine.legui.marshal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import org.reflections.ReflectionUtils;

/**
 * Created by ShchAlexander on 30.11.2017.
 */
public final class MarshalUtils {

    private MarshalUtils() {
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();

        Object field = getFielFromObjectByInClass(object, fieldName, objectClass);

        // search in inherited
        Class<?> superclass = objectClass.getSuperclass();
        while (field == null && superclass != null) {
            field = getFielFromObjectByInClass(object, fieldName, superclass);
            superclass = superclass.getSuperclass();
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
        // search boolean getter
        getterName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
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
                    if (!next.isAccessible()) {
                        next.setAccessible(true);
                    }
                    field = next.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return field;
    }

}

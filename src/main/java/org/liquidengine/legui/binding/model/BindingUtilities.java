package org.liquidengine.legui.binding.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import org.reflections.ReflectionUtils;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class BindingUtilities {

    public static boolean classTreeHasField(Class objectClass, String fieldName) {
        Class<?> superclass = objectClass;
        while (superclass != null) {
            if (classHasField(superclass, fieldName)) {
                return true;
            }
            superclass = superclass.getSuperclass();
        }
        return false;
    }

    private static boolean classHasField(Class objectClass, String fieldName) {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String boolGetterName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Set<Method> getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        Set<Method> bGetters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(boolGetterName));
        Set<Method> setters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(setterName));
        if ((!getters.isEmpty() || !bGetters.isEmpty()) && !setters.isEmpty()) {
            return true;
        }

        Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
        return !fields.isEmpty();
    }

    public static Class classTreeGetFieldType(Class objectClass, String fieldName) {
        Class fieldType = null;

        Class<?> superclass = objectClass;
        while (fieldType == null && superclass != null) {
            fieldType = getFieldType(superclass, fieldName);
            superclass = superclass.getSuperclass();
        }

        return fieldType;
    }

    private static Class getFieldType(Class objectClass, String fieldName) {
        Class fieldType = null;
        // search getter or field in this object (not searchig in inherited fields and methods)
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String boolGetterName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Set<Method> getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        Set<Method> bGetters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(boolGetterName));
        Set<Method> setters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(setterName));
        if ((!getters.isEmpty() || !bGetters.isEmpty()) && !setters.isEmpty()) {
            Method getter;
            if (!getters.isEmpty()) {
                getter = getters.iterator().next();
            } else {
                getter = bGetters.iterator().next();
            }
            fieldType = getter.getReturnType();
        }

        if (fieldType != null) {
            return fieldType;
        }

        // search boolean getter
        getterName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        if (!getters.isEmpty()) {
            Method getter = getters.iterator().next();
            fieldType = getter.getReturnType();
        }

        if (fieldType != null) {
            return fieldType;
        }

        Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
        if (!fields.isEmpty()) {
            Field next = fields.iterator().next();
            fieldType = next.getType();
        }

        return fieldType;
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Object field = null;
        // search in inherited
        Class<?> superclass = object.getClass();
        while (field == null && superclass != null) {
            field = getFieldFromObjectByInClass(object, fieldName, superclass);
            superclass = superclass.getSuperclass();
        }

        return field;
    }

    private static Object getFieldFromObjectByInClass(Object object, String fieldName, Class<?> objectClass) {
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

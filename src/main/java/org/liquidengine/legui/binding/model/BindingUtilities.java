package org.liquidengine.legui.binding.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.ReflectionUtils;

/**
 * Binding utilities class.
 *
 * @author ShchAlexander.
 */
public class BindingUtilities {

    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * Used to check if class hierarchy has specified field.
     *
     * @param objectClass class to check.
     * @param fieldName field name to search in hierarchy.
     * @return true if class hierarchy has specified field.
     */
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

    /**
     * Used to check if class has specified field.
     *
     * @param objectClass class to check.
     * @param fieldName field name to search in hierarchy.
     * @return true if class has specified field.
     */
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

    /**
     * Used to retrieve class of field in specified class hierarchy.
     *
     * @param objectClass class to retrieve field class.
     * @param fieldName field name to search field type.
     * @return field type or null if field not found.
     */
    public static Type classTreeGetFieldType(Class objectClass, String fieldName) {
        Type fieldType = null;

        Class<?> superclass = objectClass;
        while (fieldType == null && superclass != null) {
            fieldType = getFieldType(superclass, fieldName);
            superclass = superclass.getSuperclass();
        }

        return fieldType;
    }

    /**
     * Used to retrieve class of field in specified class hierarchy.
     *
     * @param objectClass class to retrieve field class.
     * @param fieldName field name to search field type.
     * @return field type or null if field not found.
     */
    public static Class classTreeGetFieldClass(Class objectClass, String fieldName) {
        Class fieldType = null;

        Class<?> superclass = objectClass;
        while (fieldType == null && superclass != null) {
            fieldType = getFieldClass(superclass, fieldName);
            superclass = superclass.getSuperclass();
        }

        return fieldType;
    }

    /**
     * Used to retrieve class of field in specified class.
     *
     * @param objectClass class to retrieve field class.
     * @param fieldName field name to search field type.
     * @return field type or null if field not found.
     */
    private static Class getFieldClass(Class objectClass, String fieldName) {
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


    /**
     * Used to retrieve class of field in specified class.
     *
     * @param objectClass class to retrieve field class.
     * @param fieldName field name to search field type.
     * @return field type or null if field not found.
     */
    private static Type getFieldType(Class objectClass, String fieldName) {
        Type fieldType = null;
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
            fieldType = getter.getGenericReturnType();
        }

        if (fieldType != null) {
            return fieldType;
        }

        // search boolean getter
        getterName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        if (!getters.isEmpty()) {
            Method getter = getters.iterator().next();
            fieldType = getter.getGenericReturnType();
        }

        if (fieldType != null) {
            return fieldType;
        }

        Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
        if (!fields.isEmpty()) {
            Field next = fields.iterator().next();
            fieldType = next.getGenericType();
        }
        return fieldType;
    }

    /**
     * Used to get field value from object going through type hierarchy.
     *
     * @param object object to get field.
     * @param fieldName field name.
     * @return field value or null if not found.
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Object fieldValue = null;
        boolean[] found = {false};
        // search in inherited
        Class<?> superclass = object.getClass();
        while (!found[0] && fieldValue == null && superclass != null) {
            fieldValue = getFieldValueFromObject(object, fieldName, superclass, found);
            superclass = superclass.getSuperclass();
        }

        return fieldValue;
    }

    /**
     * Used to get field value from object.
     *
     * @param object object to get field.
     * @param fieldName field name.
     * @param objectClass object class.
     * @param found if field found should be changed [0] element to true.
     * @return field value or null if not found.
     */
    private static Object getFieldValueFromObject(Object object, String fieldName, Class<?> objectClass, boolean[] found) {
        if (object == null) {
            throw new IllegalArgumentException("Source object cannot be null.");
        }

        Object fieldValue = null;
        // search getter or fieldValue in this object (not searchig in inherited fields and methods)
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Set<Method> getters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(getterName));
        if (!getters.isEmpty()) {
            Method getter = getters.iterator().next();
            try {
                fieldValue = getter.invoke(object);
                found[0] = true;
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
                fieldValue = getter.invoke(object);
                found[0] = true;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (fieldValue == null) {
            Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
            if (!fields.isEmpty()) {
                Field next = fields.iterator().next();
                try {
                    if (!next.isAccessible()) {
                        next.setAccessible(true);
                    }
                    fieldValue = next.get(object);
                    found[0] = true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return fieldValue;
    }

    /**
     * Used to set field value.
     *
     * @param object object to set field value.
     * @param fieldName field name.
     * @param fieldValue field value to set.
     */
    public static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        if (fieldValue == null || object == null) {
            return;
        }

        Class<?> objectClass = object.getClass();
        while (objectClass != null) {

            // search getter or field in this object (not searchig in inherited fields and methods)
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Set<Method> setters = ReflectionUtils.getMethods(objectClass, ReflectionUtils.withName(setterName));
            Class<?> fieldValueType = getNonPrimitiveType(fieldValue.getClass());
            if (!setters.isEmpty()) {
                Method setter = setters.iterator().next();
                try {
                    if (setter.getParameterCount() == 1 &&
                        getNonPrimitiveType(setter.getParameterTypes()[0]).isAssignableFrom(fieldValueType)) {
                        setter.invoke(object, fieldValue);
                        return;
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            // trying search field and set value directly to field.
            Set<Field> fields = ReflectionUtils.getFields(objectClass, ReflectionUtils.withName(fieldName));
            if (!fields.isEmpty()) {
                Field field = fields.iterator().next();
                try {
                    Class<?> fieldType = field.getType();
                    fieldType = getNonPrimitiveType(fieldType);
                    if (fieldType.isAssignableFrom(fieldValueType)) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(object, fieldValue);
                        return;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            objectClass = objectClass.getSuperclass();
        }

        LOGGER.warn("Can't set field value '" + fieldValue + "' to field '" + fieldName
            + "' in object '" + object + "' ('" + objectClass + "') cause value type is not equal to field type or there is no such field.");
    }

    /**
     * Used to retrieve non-primitive type of field (if field is primitive - it returns primitive wrapper).
     *
     * @param fieldType field type.
     * @return non-primitive field type.
     */
    private static Class<?> getNonPrimitiveType(Class<?> fieldType) {
        Class<?> type = fieldType;
        if (type.isPrimitive()) {
            type = ClassUtils.primitiveToWrapper(type);
        }
        return type;
    }
}

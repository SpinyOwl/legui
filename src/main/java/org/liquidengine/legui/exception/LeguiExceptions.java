package org.liquidengine.legui.exception;

/**
 * Created by Alexander on 26.11.2016.
 */
public enum LeguiExceptions {
    SERIALIZE_EXCEPTION("Can't serialize your component"),
    DESERIALIZE_EXCEPTION("Can't deserialize your component"),
    SERIALIZER_IS_NOT_EXIST("Can't find serializer for component type: '%s'"),
    DESERIALIZER_IS_NOT_EXIST("Can't find deserializer for component type: '%s'");

    private final String message;

    LeguiExceptions(String message) {
        this.message = message;
    }

    public String message(String... args) {
        return String.format(message, args);
    }

}

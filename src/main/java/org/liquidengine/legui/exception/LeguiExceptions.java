package org.liquidengine.legui.exception;

/**
 * Exception messages.
 */
public enum LeguiExceptions {
    MARSHALLING_EXCEPTION("Can't marshall your component"),
    UNMARSHALLING_EXCEPTION("Can't unmarshall your component"),
    MARSHALLER_IS_NOT_EXIST("Can't find marshaller for component type: '%s'"),
    UNMARSHALLER_IS_NOT_EXIST("Can't find unmarshaller for component type: '%s'"),
    GSON_REGISTRY_TYPE_EXIST("Type '%s' already exist in type registry."),
    FAILED_TO_LOAD_FONT("Failed to initialize font. (%s)"),
    FAILED_TO_LOAD_IMAGE("Failed to initialize image. (%s)"),
    FAILED_TO_READ_IMAGE("Failed to read image data. (%s)"),;

    private final String message;

    LeguiExceptions(String message) {
        this.message = message;
    }

    /**
     * Error message constructor.
     *
     * @param args error message variables
     * @return constructed message
     */
    public String message(String... args) {
        return String.format(message, (Object[]) args);
    }

    public LeguiException create(String... args) {
        return new LeguiException(message(args));
    }

    public LeguiException create(Throwable e, String... args) {
        return new LeguiException(message(args), e);
    }

}
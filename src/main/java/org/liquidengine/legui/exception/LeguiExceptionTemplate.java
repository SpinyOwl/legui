package org.liquidengine.legui.exception;

/**
 * Exception message templates. Used to build exception with predefined message.
 */
public enum LeguiExceptionTemplate {
    /**
     * Unhandled exception.
     */
    UNHANDLED_EXCEPTION("Unhandled exception: %s"),
    /**
     * Marshal exception. Used by {@link org.liquidengine.legui.marshal.json.JsonMarshaller}.
     */
    MARSHAL_EXCEPTION("Can't marshal your component"),
    /**
     * Unmarshal exception. Used by {@link org.liquidengine.legui.marshal.json.JsonMarshaller}.
     */
    UNMARSHAL_EXCEPTION("Can't unmarshal your component"),
    /**
     * Used by {@link org.liquidengine.legui.style.font.Font}.
     */
    FAILED_TO_LOAD_FONT("Failed to initialize font. (%s)"),
    /**
     * Used by {@link org.liquidengine.legui.image.BufferedImage}.
     */
    FAILED_TO_LOAD_IMAGE("Failed to initialize image. (%s)"),;

    /**
     * Exception message template.
     */
    private final String message;

    /**
     * Used to create exception templates.
     *
     * @param message message template.
     */
    LeguiExceptionTemplate(String message) {
        this.message = message;
    }

    /**
     * Error message constructor.
     *
     * @param args error message variables.
     *
     * @return constructed message.
     */
    public String message(String... args) {
        return String.format(message, (Object[]) args);
    }

    /**
     * Used to create LeguiException with specified message and provided variables.
     *
     * @param args error message variables.
     *
     * @return exception constructed with message template.
     */
    public LeguiException create(String... args) {
        return new LeguiException(message(args));
    }

    /**
     * Used to create LeguiException with specified message, {@link Throwable} cause and provided variables.
     *
     * @param args error message variables.
     * @param e throwable instance.
     *
     * @return exception constructed with message template.
     */
    public LeguiException create(Throwable e, String... args) {
        return new LeguiException(message(args), e);
    }

}
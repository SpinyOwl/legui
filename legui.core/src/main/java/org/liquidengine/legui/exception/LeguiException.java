package org.liquidengine.legui.exception;

/**
 * LeguiException is the superclass of those exceptions that can be thrown during the normal operation of the LEGUI library
 */
public class LeguiException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public LeguiException() {
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public LeguiException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public LeguiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public LeguiException(Throwable cause) {
        super(cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    public LeguiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

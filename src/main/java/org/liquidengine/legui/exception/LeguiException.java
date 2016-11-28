package org.liquidengine.legui.exception;

/**
 * Created by Alexander on 26.11.2016.
 */
public class LeguiException extends RuntimeException {
    public LeguiException() {
    }

    public LeguiException(String message) {
        super(message);
    }

    public LeguiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeguiException(Throwable cause) {
        super(cause);
    }

    public LeguiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

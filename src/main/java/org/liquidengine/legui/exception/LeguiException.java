package org.liquidengine.legui.exception;

/**
 * Created by Aliaksandr_Shcherbin on 2/6/2017.
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

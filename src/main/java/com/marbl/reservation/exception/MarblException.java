package com.marbl.reservation.exception;

import java.io.Serial;

public class MarblException extends Exception {

    @Serial
    private static final long serialVersionUID = -8451386882146270995L;

    public MarblException() {
        super();
    }

    public MarblException(String message) {
        super(message);
    }

    public MarblException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarblException(Throwable cause) {
        super(cause);
    }

    protected MarblException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.secusoft.web.tusouapi.exception;
public class TuSouClientException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new TuSouClientException with the specified message, and root
     * cause.
     *
     * @param message An error message describing why this exception was thrown.
     * @param t       The underlying cause of this exception.
     */
    public TuSouClientException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * Creates a new TuSouClientException with the specified message.
     *
     * @param message An error message describing why this exception was thrown.
     */
    public TuSouClientException(String message) {
        super(message);
    }

    public TuSouClientException(Throwable t) {
        super(t);
    }
}

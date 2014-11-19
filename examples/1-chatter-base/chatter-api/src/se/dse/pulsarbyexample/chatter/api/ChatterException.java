package se.dse.pulsarbyexample.chatter.api;

public class ChatterException extends Exception {
    public ChatterException(String message) {
        super(message);
    }

    public ChatterException(String message, Throwable cause) {
        super(message, cause);
    }
}

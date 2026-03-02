package exception;

// InvalidMobileException.java
public class InvalidMobileException extends RuntimeException {
    public InvalidMobileException(String message) {
        super(message);
    }
}
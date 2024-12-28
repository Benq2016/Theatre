package Exceptions;

public class AgeNotInBoundException extends RuntimeException {
    public AgeNotInBoundException(String message) {
        super(message);
    }
}

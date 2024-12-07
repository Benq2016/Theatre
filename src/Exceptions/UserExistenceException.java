package Exceptions;

public class UserExistenceException extends RuntimeException {
    public UserExistenceException(String message) {
        super(message);
    }
}

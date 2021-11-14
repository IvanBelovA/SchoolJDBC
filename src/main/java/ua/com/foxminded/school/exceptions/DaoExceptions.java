package ua.com.foxminded.school.exceptions;

public class DaoExceptions extends RuntimeException {

    public DaoExceptions(String message, Throwable err) {
        super(message, err);
    }

    public DaoExceptions(String message) {
        super(message);
    }
}

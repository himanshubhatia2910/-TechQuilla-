package application.backend.exceptions;

public class InvalidPatientIdException extends Exception {
    public InvalidPatientIdException(String message) {
        super(message);
    }
}

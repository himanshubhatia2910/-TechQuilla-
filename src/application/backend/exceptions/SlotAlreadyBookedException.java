package application.backend.exceptions;

public class SlotAlreadyBookedException extends Exception{
    public SlotAlreadyBookedException() {
    }

    public SlotAlreadyBookedException(String message) {
        super(message);
    }

    public SlotAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlotAlreadyBookedException(Throwable cause) {
        super(cause);
    }

    public SlotAlreadyBookedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package exception;

public class StartTimeIsInPastException extends Exception {
    public StartTimeIsInPastException(String message) {
        super(message);
    }
}

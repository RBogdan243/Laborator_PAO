package ServicePC.exception;

public class OverflowException extends Exception {
    String Message;
    public OverflowException(String message) {
        this.Message = message;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}
package ServicePC.exception;

public class EmptyStackException extends Exception {
    String Message;
    public EmptyStackException(String message) {
        this.Message = message;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}
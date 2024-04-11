package ServicePC.exception;

public class UnderflowException extends Exception {
    String Message;
    public UnderflowException(String message) {
        this.Message = message;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}
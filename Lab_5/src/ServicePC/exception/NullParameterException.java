package ServicePC.exception;

public class NullParameterException extends Exception {
    String Message;
    public NullParameterException(String message) {
        this.Message = message;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}

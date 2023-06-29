package itsamatch.itsamatchbackendspellcheckservice.Service_Interface;

public class AIModelException extends Exception {
    public AIModelException(String message) {
        super(message);
    }

    public AIModelException(String message, Throwable cause) {
        super(message, cause);
    }
}

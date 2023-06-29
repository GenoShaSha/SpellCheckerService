package itsamatch.itsamatchbackendspellcheckservice.Service;

import itsamatch.itsamatchbackendspellcheckservice.Service_Interface.AIModelException;

public class SpellCheckAIModelException extends AIModelException {
    public SpellCheckAIModelException(String message) {
        super(message);
    }

    public SpellCheckAIModelException(String message, Throwable cause) {
        super(message, cause);
    }
}

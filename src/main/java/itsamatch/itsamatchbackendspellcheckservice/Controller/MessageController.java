package itsamatch.itsamatchbackendspellcheckservice.Controller;

import itsamatch.itsamatchbackendspellcheckservice.Service_Interface.IAIModel;

import java.util.List;

public class MessageController {
    private IAIModel model;

    private List<String> inputFileTypes;
    private List<String> outputFileTypes;
    private List<String> syntaxKeywords;
    private double maxExecutionTime;


    private List<Object> getRequestsFromMessageBus() {
        return null;
    }

    private boolean checkForMatchingKeywordsAndTypes(Object message) {
        return false;
    }

    private void sendResponseToMessageBus(Object response) {
        return;
    }
}

package itsamatch.itsamatchbackendspellcheckservice.Service_Interface;

public interface IAIModel {
    String execute(String input) throws AIModelException;
}

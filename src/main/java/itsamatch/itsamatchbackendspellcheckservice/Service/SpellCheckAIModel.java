package itsamatch.itsamatchbackendspellcheckservice.Service;

import itsamatch.itsamatchbackendspellcheckservice.Service_Interface.IAIModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SpellCheckAIModel implements IAIModel {
    private static final String API_KEY = "vGdbk2v6GNRki3aggOqZBkXtgPolsu3i";
    private static final String ENDPOINT = "https://api.apilayer.com/spell/spellchecker";

    @Override
    public String execute(String input) throws SpellCheckAIModelException {
        String text = input;

        System.out.printf("Checking spelling for text: " + text);

        try {
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            String url = ENDPOINT + "?q=" + encodedText;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("apikey", API_KEY);

            int responseCode = con.getResponseCode();
            Scanner scanner = new Scanner(con.getInputStream());
            StringBuilder response = new StringBuilder();

            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }

            scanner.close();
            return response.toString();
        } catch (IOException e) {
            throw new SpellCheckAIModelException(e.getMessage());
        }
    }
}




package data.GoogleTranslateAPI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPI{
    private StringBuilder translatedText = new StringBuilder();
    StringProperty stringProperty = new SimpleStringProperty("");

    public static final String GOOGLE_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbydiJhREoeyM7Z4DoBwCSvdO1dwhUoWEi6bVCqTBde0Nem_fA42hNRRhFiFCYyk35RXzg/exec";

    private String generateURL(String sourcesLanguage, String targetLanguage, String text) throws UnsupportedEncodingException {
        String url = GOOGLE_SCRIPT_URL +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + targetLanguage +
                "&source=" + sourcesLanguage;
        return url;
    }

    public void translate(String text, String sourceLanguge, String targetLanguage) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(generateURL(sourceLanguge, targetLanguage, text));
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    translatedText.setLength(0);
                    String inputLine;
                    while ((inputLine = bufferedReader.readLine()) != null) {
                        translatedText.append(inputLine);
                    }
                    stringProperty.setValue(translatedText.toString());
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public StringProperty getStringProperty() {
        return stringProperty;
    }
}

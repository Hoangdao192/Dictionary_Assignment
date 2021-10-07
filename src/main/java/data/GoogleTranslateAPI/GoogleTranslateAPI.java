package data.GoogleTranslateAPI;

import org.apache.commons.codec.language.bm.Languages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleTranslateAPI {
    class Language {
        public static final String ENGLISH = "en";
        public static final String VIETNAMESE = "vi";
    }
    public static final String GOOGLE_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbydiJhREoeyM7Z4DoBwCSvdO1dwhUoWEi6bVCqTBde0Nem_fA42hNRRhFiFCYyk35RXzg/exec";

    private String generateURL(String sourcesLanguage, String targetLanguage, String text) throws UnsupportedEncodingException {
        String url = GOOGLE_SCRIPT_URL +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + targetLanguage +
                "&source=" + sourcesLanguage;
        return url;
    }


    public String translate(String text) throws IOException {
        URL url = new URL(generateURL(Language.ENGLISH, Language.VIETNAMESE, text));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        StringBuilder translatedText = new StringBuilder();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            translatedText.append(inputLine);
        }
        bufferedReader.close();
        return translatedText.toString();
    }
}

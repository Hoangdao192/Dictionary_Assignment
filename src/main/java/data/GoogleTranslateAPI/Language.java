package data.GoogleTranslateAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Language {
    public static HashMap<String, String> languageMap = null;
    public static final String AFRIKAANS = "af";
    public static final String ALBANIAN = "sq";
    public static final String AMHARIC = "am";
    public static final String ARABIC = "ar";
    public static final String ARMENIAN = "hy";
    public static final String AZERBAIJANI = "az";
    public static final String BASQUE = "eu";
    public static final String BELARUSIAN = "be";
    public static final String BENGALI = "bn";
    public static final String BOSNIAN = "bs";
    public static final String BULGARIAN = "bg";
    public static final String CATALAN = "ca";
    public static final String CEBUANO = "ceb";
    public static final String CHINESE = "zh";
    public static final String CORSICAN = "co";
    public static final String CROATIAN = "hr";
    public static final String CZECH = "cs";
    public static final String DANISH = "da";
    public static final String DUTCH = "nl";
    public static final String ENGLISH = "en";
    public static final String ESPERANTO = "eo";
    public static final String ESTONIAN = "et";
    public static final String FINNISH = "fi";
    public static final String FRENCH = "fr";
    public static final String FRISIAN = "fy";
    public static final String GALICIAN = "gl";
    public static final String GEORGIAN = "ka";
    public static final String GERMAN = "de";
    public static final String GREEK = "el";
    public static final String GUJARATI = "gu";
    public static final String HAITIAN = "ht";
    public static final String HAUSA = "ha";
    public static final String HAWAIIAN = "haw";
    public static final String HEBREW = "he";
    public static final String HINDI = "hi";
    public static final String HMONG = "hmn";
    public static final String HUNGARIAN = "hu";
    public static final String ICELANDIC = "is";
    public static final String IGBO = "ig";
    public static final String INDONESIAN = "id";
    public static final String IRISH = "ga";
    public static final String ITALIAN = "it";
    public static final String JAPANESE = "ja";
    public static final String JAVANESE = "jv";
    public static final String KANNADA = "kn";
    public static final String KAZAKH = "kk";
    public static final String KHMER = "km";
    public static final String KINYARWANDA = "rw";
    public static final String KOREAN = "ko";
    public static final String KURDISH = "ku";
    public static final String KYRGYZ = "ky";
    public static final String LAO = "lo";
    public static final String LATVIAN = "lv";
    public static final String LITHUANIAN = "lt";
    public static final String LUXEMBOURGISH = "lb";
    public static final String MACEDONIAN = "mk";
    public static final String MALAGASY = "mg";
    public static final String MALAY = "ms";
    public static final String MALAYALAM = "ml";
    public static final String MALTESE = "mt";
    public static final String MAORI = "mi";
    public static final String MARATHI = "mr";
    public static final String MONGOLIAN = "mn";
    public static final String MYANMAR = "my";
    public static final String NEPALI = "ne";
    public static final String NORWEGIAN = "no";
    public static final String NYANJA = "ny";
    public static final String ODIA = "or";
    public static final String PASHTO = "ps";
    public static final String PERSIAN = "fa";
    public static final String POLISH = "pl";
    public static final String PORTUGUESE = "pt";
    public static final String PUNJABI = "pa";
    public static final String ROMANIAN = "ro";
    public static final String RUSSIAN = "ru";
    public static final String SAMOAN = "sm";
    public static final String SCOTS = "gd";
    public static final String SERBIAN = "sr";
    public static final String SESOTHO = "st";
    public static final String SHONA = "sn";
    public static final String SINDHI = "sd";
    public static final String SINHALA = "si";
    public static final String SLOVAK = "sk";
    public static final String SLOVENIAN = "sl";
    public static final String SOMALI = "so";
    public static final String SPANISH = "es";
    public static final String SUNDANESE = "su";
    public static final String SWAHILI = "sw";
    public static final String SWEDISH = "sv";
    public static final String TAGALOG = "tl";
    public static final String TAJIK = "tg";
    public static final String TAMIL = "ta";
    public static final String TATAR = "tt";
    public static final String TELUGU = "te";
    public static final String THAI = "th";
    public static final String TURKISH = "tr";
    public static final String TURKMEN = "tk";
    public static final String UKRAINIAN = "uk";
    public static final String URDU = "ur";
    public static final String UYGHUR = "ug";
    public static final String UZBEK = "uz";
    public static final String VIETNAMESE = "vi";
    public static final String WELSH = "cy";
    public static final String XHOSA = "xh";
    public static final String YIDDISH = "yi";
    public static final String YORUBA = "yo";
    public static final String ZULU = "zu";

    private static final String DEFAULT_LANGUAGE_PATH = "src/main/resources/data/Languages.txt";

    public static ArrayList<String> getLanguageList() {
        ArrayList<String> languages = new ArrayList<String>();
        try {
            File file = new File(DEFAULT_LANGUAGE_PATH);
            System.out.println(file.getAbsolutePath());
            BufferedReader fileReader = new BufferedReader(new FileReader(DEFAULT_LANGUAGE_PATH));

            String read = "";
            while ((read = fileReader.readLine()) != null) {
                languages.add(read.split(" ")[0]);
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return languages;
    }

    public static void initLanguageMap() {
        if (languageMap != null) {
            return;
        }
        languageMap = new HashMap<String, String>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(DEFAULT_LANGUAGE_PATH));
            String read = "";
            while ((read = fileReader.readLine()) != null) {
                languageMap.put(read.split(" ")[0], read.split(" ")[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLanguage(String targetLanguage) {
        if (languageMap == null) {
            initLanguageMap();
        }
        if (languageMap.containsKey(targetLanguage)) {
            return languageMap.get(targetLanguage);
        }
        //  Default
        return VIETNAMESE;
    }
}

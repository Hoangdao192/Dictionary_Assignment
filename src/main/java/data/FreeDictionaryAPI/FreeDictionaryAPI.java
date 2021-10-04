package data.FreeDictionaryAPI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import data.FreeDictionaryAPI.word.Definition;
import data.FreeDictionaryAPI.word.Phonetic;
import data.FreeDictionaryAPI.word.Word;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class Language {
    public final String VIETNAMESE = "vn";
    public final String ENGLISH = "en";
}

public class FreeDictionaryAPI {
    public URL generateAPIURL(String wordTarget) throws Exception{
        return new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + wordTarget);
    }

    public String getJSONFromURL(URL url) throws Exception{
        URLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");

        int read = 0;
        StringBuilder content = new StringBuilder();
        while ((read = inputStream.read()) != -1) {
            content.append((char) read);
        }

        return content.toString();
    }

    /**
     * Lấy dữ liệu tất cả các từ được trả về từ json
     * @param json
     * @return
     * @throws Exception
     */
    public ArrayList<Word> getDataFromJSON(String json) throws Exception{
        ArrayList<Word> words = new ArrayList<Word>();

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(json);
        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            Word newWord = new Word();

            //  Từ vựng
            String word = (String) jsonObject.get("word");
            newWord.setWord_target(word);

            //  Các cách phát âm
            JSONArray sounds = (JSONArray) jsonObject.get("phonetics");
            for (int j = 0; j < sounds.size(); ++j) {
                JSONObject sound = (JSONObject) sounds.get(j);
                //  Cách phát âm
                String pronounce = (String) sound.get("text");
                //  Link Âm thanh
                String mp3 = (String) sound.get("audio");
                newWord.getPhonetics().add(new Phonetic(pronounce, mp3));
            }

            //  Các nghĩa của từ
            JSONArray meanings = (JSONArray) jsonObject.get("meanings");
            for (int j = 0; j < meanings.size(); ++j) {
                JSONObject meaning = (JSONObject) meanings.get(j);

                //  Loại từ
                String kind = (String) meaning.get("partOfSpeech");

                JSONArray definitions = (JSONArray) meaning.get("definitions");
                for (int k  = 0; k < definitions.size(); ++k) {
                    JSONObject definition = (JSONObject) definitions.get(k);
                    //  Nghĩa
                    String mean = (String) definition.get("definition");

                    //  Từ đồng nghĩa
                    JSONArray synonyms = (JSONArray) definition.get("synonyms");
                    ArrayList<String> synonymsSave = new ArrayList<String>();
                    for (int l = 0; l < synonyms.size(); ++l) {
                        synonymsSave.add((String) synonyms.get(l));
                    }

                    //  Từ trái nghĩa
                    JSONArray antonyms = (JSONArray) definition.get("antonyms");
                    ArrayList<String> antonymsSave = new ArrayList<String>();
                    for (int l = 0; l < antonyms.size(); ++l) {
                        antonymsSave.add((String) antonyms.get(l));
                    }

                    Definition newDefinition = new Definition(mean, kind);
                    newDefinition.getSynonyms().addAll(synonymsSave);
                    newDefinition.getAntonyms().addAll(antonymsSave);
                    newWord.getDefinitions().add(newDefinition);
                }
            }
            words.add(newWord);
        }

        return words;
    }

    /**
     * Kết quả mà getDataFromJSON có thể chứa các từ giống nhau nhưng khác nghĩa.
     * Hàm này sẽ hợp nhất các từ đó thành 1 từ với nhiều nghĩa khác nhau.
     * @param words
     * @return
     */
    private ArrayList<Word> filterWord(ArrayList<Word> words) {
        int i = 0;
        while (i < words.size() - 1) {
            int j = i + 1;
            while (j < words.size()) {
                if (words.get(i).sameWordTarget(words.get(j))) {
                    Word mergeWord = words.get(i);
                    mergeWord.getPhonetics().addAll(words.get(j).getPhonetics());
                    mergeWord.getDefinitions().addAll(words.get(j).getDefinitions());
                    words.remove(j);
                } else {
                    ++j;
                }
            }
            ++i;
        }
        return words;
    }

    public ArrayList<Word> getSuggestedWord(String word) {
        ArrayList<Word> suggestedWord = new ArrayList<Word>();
        try {
            URL dictionaryURL = generateAPIURL(word);
            String json = getJSONFromURL(dictionaryURL);
            suggestedWord = getDataFromJSON(json);
            suggestedWord = filterWord(suggestedWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suggestedWord;
    }

    public void testReadJson() {
        try {
            URL url = generateAPIURL("agriculture");
            String json = getJSONFromURL(url);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

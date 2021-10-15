package data.FreeDictionaryAPI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import data.Dictionary;
import data.FreeDictionaryAPI.word.Definition;
import data.FreeDictionaryAPI.word.Phonetic;
import data.FreeDictionaryAPI.word.FreeDictionaryWord;
import data.Word;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FreeDictionaryAPI extends Dictionary {
    private URL generateAPIURL(String wordTarget) throws Exception{
        return new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + wordTarget);
    }

    /**
     * Lấy dữ liệu được trả về dưới dạng JSON
     */
    private String getJSONFromURL(URL url){
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String read;
            StringBuilder content = new StringBuilder();
            while ((read = bufferedReader.readLine()) != null) {
                content.append(read);
            }
            return content.toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

       return null;
    }

    /**
     * Lấy dữ liệu tất cả các từ được trả về từ json
     */
    public ArrayList<FreeDictionaryWord> getDataFromJSON(String json) throws Exception{
        ArrayList<FreeDictionaryWord> freeDictionaryWords = new ArrayList<FreeDictionaryWord>(0);

        if (json == null) {
            return freeDictionaryWords;
        }

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(json);
        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            FreeDictionaryWord newFreeDictionaryWord = new FreeDictionaryWord();

            //  Từ vựng
            String word = (String) jsonObject.get("word");
            newFreeDictionaryWord.setWord_target(word);

            //  Các cách phát âm
            JSONArray sounds = (JSONArray) jsonObject.get("phonetics");
            for (int j = 0; j < sounds.size(); ++j) {
                JSONObject sound = (JSONObject) sounds.get(j);
                //  Cách phát âm
                String pronounce = (String) sound.get("text");
                //  Link Âm thanh
                String mp3 = (String) sound.get("audio");
                newFreeDictionaryWord.getPhoneticsOrigin().add(new Phonetic(pronounce, mp3));
                newFreeDictionaryWord.setSound(pronounce);
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
                    newDefinition.getSynonymsOrigin().addAll(synonymsSave);
                    newDefinition.getAntonymsOrigin().addAll(antonymsSave);
                    newFreeDictionaryWord.getDefinitions().add(newDefinition);
                }
                String mainSound = "";
            }
            freeDictionaryWords.add(newFreeDictionaryWord);
        }

        return freeDictionaryWords;
    }

    /**
     * Kết quả mà getDataFromJSON có thể chứa các từ giống nhau nhưng khác nghĩa.
     * Hàm này sẽ hợp nhất các từ đó thành 1 từ với nhiều nghĩa khác nhau.
     */
    private ArrayList<FreeDictionaryWord> mergeDuplicateWords(ArrayList<FreeDictionaryWord> words) {
        int i = 0;
        while (i < words.size() - 1) {
            int j = i + 1;
            while (j < words.size()) {
                if (words.get(i).sameWordTarget(words.get(j))) {
                    FreeDictionaryWord mergeWord = words.get(i);
                    mergeWord.getPhoneticsOrigin().addAll(words.get(j).getPhonetics());
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

    @Override
    public ArrayList<Word> searchWord(String word) {
        ArrayList<FreeDictionaryWord> suggestedWord = new ArrayList<FreeDictionaryWord>();
        try {
            URL dictionaryURL = generateAPIURL(word);
            String json = getJSONFromURL(dictionaryURL);
            suggestedWord = getDataFromJSON(json);
            suggestedWord = mergeDuplicateWords(suggestedWord);
            //  Generate wordExplain theo định dạng của Word gốc
            for (int i = 0; i < suggestedWord.size(); ++i) {
                suggestedWord.get(i).setWord_explain(suggestedWord.get(i).generateWordExplain());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Word> ret = new ArrayList<Word>();
        ret.addAll(suggestedWord);
        return ret;
    }
}

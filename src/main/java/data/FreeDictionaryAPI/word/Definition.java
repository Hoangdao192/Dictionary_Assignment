package data.FreeDictionaryAPI.word;

import java.util.ArrayList;
import java.util.HashSet;

public class Definition {
    String partOfSpeech;
    String definition;

    HashSet<String> synonyms = new HashSet<String>();
    HashSet<String> antonyms = new HashSet<String>();

    public Definition(String definition, String partOfSpeech) {
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms.clear();
        for (int i = 0; i < synonyms.size(); ++i) {
            this.synonyms.add(synonyms.get(i));
        }
    }

    public void setAntonyms(ArrayList<String> antonyms) {
        this.antonyms.clear();
        for (int i = 0; i < antonyms.size(); ++i) {
            this.antonyms.add(antonyms.get(i));
        }
    }

    public String getPartOfSpeech() {
        return this.partOfSpeech;
    }

    public String getDefinition() {
        return this.definition;
    }

    public HashSet<String> getSynonyms() {
        return this.synonyms;
    }

    public HashSet<String> getAntonyms() {
        return this.antonyms;
    }
}

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

    private String synonymsToHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] synonyms = getSynonyms().toArray(new String[getSynonyms().size()]);
        if (synonyms.length > 0) {
            stringBuilder.append("<li>");
            stringBuilder.append("Từ đồng nghĩa: ");
            stringBuilder.append("<ul>");
            for (int j = 0; j < synonyms.length; ++j) {
                stringBuilder.append("<li><b>" + synonyms[j] + "</b></li>");
            }
            stringBuilder.append("</ul>");
            stringBuilder.append("</li>");
        }
        return stringBuilder.toString();
    }

    private String antonymsToHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] antonyms = getAntonyms().toArray(new String[getAntonyms().size()]);
        if (antonyms.length > 0) {
            stringBuilder.append("<li>");
            stringBuilder.append("Từ trái nghĩa:");
            stringBuilder.append("<ul>");
            for (int j = 0; j < antonyms.length; ++j) {
                stringBuilder.append("<li><b>" + antonyms[j] + "</b></li>");
            }
            stringBuilder.append("</ul>");
            stringBuilder.append("</li>");
        }
        return stringBuilder.toString();
    }

    public String toHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b><i>" + getPartOfSpeech() + "</i></b>");
        stringBuilder.append("<li><font color='#cc0000'><b>" + getDefinition() + "</b></font><ul>");
        stringBuilder.append(synonymsToHTML());
        stringBuilder.append(antonymsToHTML());
        stringBuilder.append("</ul>");
        stringBuilder.append("</li>");
        stringBuilder.append("<br>");
        return stringBuilder.toString();
    }
}

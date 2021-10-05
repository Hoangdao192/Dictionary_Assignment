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

    public HashSet<String> getSynonymsOrigin() {
        return this.synonyms;
    }

    public HashSet<String> getAntonymsOrigin() {
        return this.antonyms;
    }

    public ArrayList<String> getSynonyms() {
        ArrayList<String> ret = new ArrayList<String>();
        String[] synonymsArr = synonyms.toArray(new String[synonyms.size()]);
        for (int i = 0; i < synonymsArr.length; ++i) {
            ret.add(synonymsArr[i]);
        }
        return ret;
    }

    public ArrayList<String> getAntonyms() {
        ArrayList<String> ret = new ArrayList<String>();
        String[] antonymsArr = antonyms.toArray(new String[antonyms.size()]);
        System.out.println(antonyms.size());
        System.out.println(antonymsArr.length);
        for (int i = 0; i < antonymsArr.length; ++i) {
            ret.add(antonymsArr[i]);
        }
        return ret;
    }

    private String synonymsToHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> synonyms = getSynonyms();
        if (synonyms.size() > 0) {
            stringBuilder.append("<li>");
            stringBuilder.append("Từ đồng nghĩa: ");
            stringBuilder.append("<ul>");
            for (int i = 0; i < synonyms.size(); ++i) {
                stringBuilder.append("<li><b>" + synonyms.get(i) + "</b></li>");
            }
            stringBuilder.append("</ul>");
            stringBuilder.append("</li>");
        }
        return stringBuilder.toString();
    }

    private String antonymsToHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> antonyms = getAntonyms();
        if (antonyms.size() > 0) {
            stringBuilder.append("<li>");
            stringBuilder.append("Từ trái nghĩa:");
            stringBuilder.append("<ul>");
            for (int i = 0; i < antonyms.size(); ++i) {
                stringBuilder.append("<li><b>" + antonyms.get(i) + "</b></li>");
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

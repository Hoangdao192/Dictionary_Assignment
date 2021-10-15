package data.FreeDictionaryAPI.word;

import data.Word;

import java.util.ArrayList;
import java.util.HashSet;

public class FreeDictionaryWord extends data.Word{
    HashSet<Phonetic> phonetics = new HashSet<Phonetic>();
    ArrayList<Definition> definitions = new ArrayList<Definition>();

    public FreeDictionaryWord() {
        this.setWord_target("hello");
        this.setWord_explain("hello");
    }

    public FreeDictionaryWord(String wordTarget) {
        this.setWord_target(wordTarget);
    }

    public HashSet<Phonetic> getPhoneticsOrigin() {
        return this.phonetics;
    }

    @Override
    public ArrayList<Phonetic> getPhonetics() {
        ArrayList<Phonetic> ret = new ArrayList<Phonetic>();
        Phonetic[] phoneticArr = phonetics.toArray(new Phonetic[phonetics.size()]);
        for (int i = 0; i < phoneticArr.length; ++i) {
            ret.add(phoneticArr[i]);
        }
        int index = 0;
        while (index < ret.size()) {
            if (ret.get(index).getAudio().equals("")) {
                ret.remove(index);
            } else {
                ++index;
            }
        }
        return ret;
    }

    public ArrayList<Definition> getDefinitions() {
        return this.definitions;
    }

    public boolean sameWordTarget(FreeDictionaryWord other) {
        return this.getWord_target().equals(other.getWord_target());
    }

    public String generateWordExplain() {
        StringBuilder stringBuilder = new StringBuilder();
        String mainPronounce = "";
        if (phonetics.size() > 0) {
            mainPronounce = getPhonetics().get(0).getPronounce();
        }
        stringBuilder.append("<i>" + getWord_target() + " /" + mainPronounce + "/</i>");
        stringBuilder.append("<ul>");
        for (int i = 0; i < definitions.size(); ++i) {
            Definition definition = definitions.get(i);
            stringBuilder.append(definition.toHTML());
        }
        stringBuilder.append("</ul>");
        return stringBuilder.toString();
    }
}

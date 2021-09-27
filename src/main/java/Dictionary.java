import java.util.ArrayList;
import java.util.Locale;

public class Dictionary {
    ArrayList<Word> words;

    public Dictionary() {
        this.words = new ArrayList<Word>();
    }

    public void add(String wordTarget, String wordExplain) {
        Word newWord = new Word(wordTarget, wordExplain);
        this.words.add(newWord);
    }

    public String getThisWordExplain(String wordTarget) {
        for (int i = 0; i < this.words.size(); ++i) {
            Word currentWord = this.words.get(i);
            if (wordTarget.equalsIgnoreCase(currentWord.getWordTarget())) {
                return currentWord.getWordExplain();
            }
        }

        return "NULL";
    }

    public int size() {
        return this.words.size();
    }

    public Word get(int position) {
        if (position >= this.words.size()) {
            //Word error = new Word("NULL", "NULL");
            return null;
        }

        return this.words.get(position);
    }

    public boolean erase(String wordTarget) {
        for (int i = 0; i < words.size(); ++i) {
            if (wordTarget.equalsIgnoreCase(words.get(i).getWordTarget())) {
                words.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean erase(int index) {
        if (index >= words.size()) {
            return false;
        }
        words.remove(index);
        return true;
    }
}

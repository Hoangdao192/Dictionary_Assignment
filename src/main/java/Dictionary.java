import java.util.ArrayList;

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
            if (wordTarget.equals(currentWord.getWordTarget())) {
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
            Word error = new Word("NULL", "NULL");
            return error;
        }

        return this.words.get(position);
    }
}

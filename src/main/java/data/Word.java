package data;

public class Word implements Comparable {
    //word_target (từ mới), word_explain (giải nghĩa)
    private String word_target;
    private String word_explain;
    private String sound = "";

    public void setWord_target(String w) {
        word_target = w;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_explain(String w) {
        word_explain = w;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Word() {
        word_explain = "";
        word_target = "";
    }

    public Word(String wordTarget, String wordExplain) {
        this.word_target = wordTarget;
        this.word_explain = wordExplain;
    }

    public void setWord(Word w) {
        word_target = w.getWord_target();
        word_explain = w.getWord_explain();
    }

    public int compareTo(Word w) {
        return word_target.compareTo(w.getWord_target());
    }

    @Override
    public int compareTo(Object o) {
        Word w = (Word) o;
        return word_target.compareTo(w.getWord_target());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Word other = (Word) o;
        if (this.word_target.equals(other.word_target)
            && this.word_explain.equals(other.word_explain)) {
            return true;
        }
        return false;
    }
}

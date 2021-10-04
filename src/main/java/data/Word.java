package data;
public class Word implements Comparable {
    //word_target (từ mới), word_explain (giải nghĩa)
    private String word_target;
    private String word_explain;

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
}
